package com.WebJava.cats.api.util;

import lombok.Builder;

@Builder
public record InvalidatedParams(String cause, String attribute) {}
