package com.nowcoder.community.util;

import com.mysql.cj.log.LogFactory;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author:xiaoyang
 * @Title: SensitiveFilter
 * @ProjectName: community
 * @Description: TODO
 * @date: 2021/09/18 12:58
 */
@Component
public class SensitiveFilter {
    //声明日志
    private static final Logger logger = LoggerFactory.getLogger(SensitiveFilter.class);
    //替换符
    private static final String REPLACEMENT = "***";
    //根节点
    private TrieNode rootNode = new TrieNode();

    //初始化，敏感词前缀树在服务启动时被初始化，在Bean创建时构建敏感词前缀树
    @PostConstruct
    public void init() {
        //从类路径中读出敏感词字符

        try(    //创建字节流
                InputStream is =  this.getClass().getClassLoader().getResourceAsStream("sensitive-words.txt");
                 //创建字符流
                BufferedReader reader = new BufferedReader(new InputStreamReader(is))
        )
        {
            //读取字符流，将字符串添加到前缀树中
            String keyword;
            while ((keyword = reader.readLine()) != null){
                this.addKeyWord(keyword);
        }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    //将一个敏感词添加到前缀树中
    private void addKeyWord(String keyword) {
        TrieNode tempNode =rootNode;
        //遍历字符串每一个字符
        for (int i = 0; i < keyword.length(); i++) {
            char c = keyword.charAt(i);
            //获取当前字符作为key的节点，如果为空，添加c为key的子节点
            TrieNode subNode = tempNode.getSubNode(c);
            if (subNode == null) {
                subNode = new TrieNode();
                tempNode.addSubNodes(c, subNode);
            }
            //指向子节点，进入下一轮循环
            tempNode = subNode;

            if (i == keyword.length() - 1) {
                tempNode.setKeywordEnd(true);
            }
        }
    }
    /*
     *
     * @Description: 过滤敏感词
     * @param:
     * @return * @return java.lang.String
     * @author xiaoyang
     */
    public String filter(String text) {
        if (StringUtils.isBlank(text)) {
            return null;
        }
        //指针1
        TrieNode tempNode = rootNode;
        // 指针2
        int begin = 0;
        // 指针3
        int position = 0;
        // 结果
        StringBuilder sb = new StringBuilder();
        //终止条件
        while (position < text.length()) {
            if (position < text.length()){
                char c = text.charAt(position);

                // 跳过符号
                if (isSymbol(c)) {
                    // 若指针1处于根节点,将此符号计入结果,让指针2向下走一步
                    if (tempNode == rootNode) {
                        sb.append(c);
                        begin++;
                    }
                    // 无论符号在开头或中间,指针3都向下走一步
                    position++;
                    continue;
                }

                // 检查下级节点
                tempNode = tempNode.getSubNode(c);
                if (tempNode == null) {
                    // 以begin开头的字符串不是敏感词
                    sb.append(text.charAt(begin));
                    // 进入下一个位置
                    position = ++begin;
                    // 重新指向根节点
                    tempNode = rootNode;
                } else if (tempNode.isKeywordEnd()) {
                    // 发现敏感词,将begin~position字符串替换掉
                    sb.append(REPLACEMENT);
                    // 进入下一个位置
                    begin = ++position;
                    // 重新指向根节点
                    tempNode = rootNode;
                } else {
                    // 检查下一个字符
                    position++;
                }
            }else {//position越界后仍未匹配
                sb.append(text.charAt(begin));
                position = begin++;
                tempNode = rootNode;
            }
        }

        // 将最后一批字符计入结果
        sb.append(text.substring(begin));

        return sb.toString();
    }
    // 判断是否为符号
    private boolean isSymbol(Character c) {
        // 0x2E80~0x9FFF 是东亚文字范围
        return !CharUtils.isAsciiAlphanumeric(c) && (c < 0x2E80 || c > 0x9FFF);
    }

    //前缀树结构
    private class TrieNode {
        //关键词结束标识
        private boolean isKeywordEnd = false;

        //子节点（key是下级字符，value是下级节点）
        private Map<Character, TrieNode> subNodes = new HashMap<>();

        //getter setter
        public boolean isKeywordEnd() {
            return isKeywordEnd;
        }

        public void setKeywordEnd(boolean keywordEnd) {
            isKeywordEnd = keywordEnd;
        }
        //添加子节点
        public void addSubNodes(Character c, TrieNode node) {
            subNodes.put(c, node);
        }
        //获取子节点
        public TrieNode getSubNode(Character c) {
            return subNodes.get(c);
        }
    }
}
