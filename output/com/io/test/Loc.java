package com.io.test;

import org.codehaus.jackson.annotate.JsonProperty;
import java.util.*;

public class Loc {
	@JsonProperty("phc") private String phc;
	@JsonProperty("state") private String state;
	@JsonProperty("district") private String district;
	@JsonProperty("taluk") private String taluk;
	@JsonProperty("sub_center") private String sub_center;
}
