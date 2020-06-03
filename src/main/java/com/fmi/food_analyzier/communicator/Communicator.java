package com.fmi.food_analyzier.communicator;

import com.fmi.food_analyzier.request.RequestData;

public interface Communicator {

  void printUsage();

  void printEnterPoint();

  void printRequestResult(String result);

  RequestData readUserRequest();
}
