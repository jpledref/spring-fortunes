package com.example.spring.fortunes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring.fortunes.dao.FortuneDAO;
import com.example.spring.fortunes.models.Fortune;

@RestController
public class FortuneController {

  @Autowired
  private FortuneDAO fortuneDAO;

  @RequestMapping(value = "/", produces = { "text/html" }, method = RequestMethod.GET)
  public String getOne(@RequestParam(required = false) String raw) {
    Fortune fortune = fortuneDAO.findOne();

    StringBuilder sb = new StringBuilder();

    if (Boolean.parseBoolean(raw)) {
      sb.append(fortune.getContent());
    } else {
      sb.append("<div style=\"height:100%;\">");
      sb.append("<div style=\"text-align:center;position: relative;top:40%;\">");
      sb.append("<pre style=\"text-align:left;display:inline-block;font-weight:bold;font-size:1.5em;\">");
      sb.append(fortune.getContent());
      sb.append("</pre></div></div>");
    }

    return sb.toString();
  }

  @RequestMapping(value = "/all", produces = { "application/json" }, method = RequestMethod.GET)
  public List<Fortune> getAll() {
    List<Fortune> fortunes = fortuneDAO.findAll();
    return fortunes;
  }

}
