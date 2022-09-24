package com.algafoodapi.error;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Data
public class Errors extends ArrayList<HashMap<String, List<LinkedHashMap>>> {
}
