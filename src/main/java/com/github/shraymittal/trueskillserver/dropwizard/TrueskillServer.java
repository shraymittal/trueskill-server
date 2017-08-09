package com.github.shraymittal.trueskillserver.dropwizard;

import com.github.shraymittal.trueskillserver.data.Data;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class TrueskillServer extends Application<TrueskillServerConfiguration> {

  private static Data data = setData();

  public static void main(String[] args) throws Exception {
    new TrueskillServer().run(args);
  }

  @Override
  public void run(TrueskillServerConfiguration config, Environment environment) {
    final TrueskillServerResource resource = new TrueskillServerResource();
    environment.jersey().register(resource);
  }

  private static Data setData() {
    try {
      throw new Exception();
      /*FileInputStream in = new FileInputStream("data.ser");
      BufferedInputStream buffer = new BufferedInputStream(in);
      ObjectInput oIn = new ObjectInputStream(buffer);
      return (Data) oIn.readObject();*/
    } catch (Exception e) {
      return new Data();
    }
  }

  public static void writeData() {
    try {
      FileOutputStream out = new FileOutputStream("data.ser");
      ObjectOutputStream oOut = new ObjectOutputStream(out);
      oOut.writeObject(data);
    } catch (IOException e) {
      System.err.println("Write data failed");
    }
  }

  public static Data getData() {
    return data;
  }

}
