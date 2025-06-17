package edu.pzks.projtest.Utils;


/*
  @author   nick
  @project   proj-test
  @class  Utils
  @version  1.0.0 
  @since 04.05.25 - 09.06
*/

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {
    public static String toJson(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }

}
