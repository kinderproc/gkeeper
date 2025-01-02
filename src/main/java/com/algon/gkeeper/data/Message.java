package com.algon.gkeeper.data;

import java.util.UUID;

public record Message(UUID id, MessageCode messageCode, String data) {}
