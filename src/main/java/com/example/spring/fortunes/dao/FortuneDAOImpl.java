package com.example.spring.fortunes.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Repository;

import com.example.spring.fortunes.models.Fortune;
import com.example.spring.fortunes.threads.FortunesLoader;

@Repository
public class FortuneDAOImpl implements FortuneDAO {

  public List<Fortune> fortunes = new ArrayList<>();

  private FortuneDAOImpl() {
    FortunesLoader fl = new FortunesLoader();
    fl.setFortuneDAO(this);
    Thread thread = new Thread(fl);
    thread.start();
  }

  @Override
  public List<Fortune> findAll() {
    return fortunes;
  }

  @Override
  public Fortune findOne() {
    return fortunes.get(new Random().nextInt(fortunes.size()));
  }

  @Override
  public void save(List<Fortune> list) {
    fortunes = list;
  }

}
