package com.algon.gkeeper.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GameMode {
  SINGLE("Single player"),
  MULTI("Multi Player");

  private final String name;
}
