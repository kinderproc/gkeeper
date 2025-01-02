package com.algon.gkeeper.data;

import java.util.List;
import java.util.UUID;

public record GameDto(
    UUID gameId,
    String name,
    String genre,
    List<Platform> platforms,
    List<GameMode> gameModes,
    Boolean isOnline,
    Long downloadSizeMB) {}
