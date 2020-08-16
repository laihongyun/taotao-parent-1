package com.taotao.search.controller;

import com.taotao.search.service.ItemSearchService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @program: taotao-search
 * @description:
 * @author: lhy
 * @create: 2020-07-30 09:30
 **/
@Controller
public class ItemSearchController {

    @Autowired
    private ItemSearchService itemsearchService;

    @GetMapping("/search")
    public String search(@RequestParam(value="q",required = false)String keyword,
                         @RequestParam(value="page",defaultValue = "1")Integer page,
                         Model model) throws UnsupportedEncodingException {

        //get请求会中文乱码，需要转译
        if(StringUtils.isNoneBlank(keyword)){
            keyword = new String(keyword.getBytes("iso-8859-1"),"utf-8");

        }

        //根据提交参数，查询页面信息
        Map<String,Object> maps = itemsearchService.search(page,keyword,20);

        model.addAttribute("query",keyword);
        model.addAttribute("page",page);

        if(maps!=null && maps.size()>0){
            model.addAllAttributes(maps);
        }

        return "search";

    }

}
