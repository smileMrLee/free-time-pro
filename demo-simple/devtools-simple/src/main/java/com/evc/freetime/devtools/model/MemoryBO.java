
package com.evc.freetime.devtools.model;

import lombok.Data;

@Data
@SuppressWarnings("unused")
public class MemoryBO {

    private String query;
    private Object resulBmap;
    private ResultAmap resultAmap;
    private String searchUrl;
    private String searchUrlBmap;

}
