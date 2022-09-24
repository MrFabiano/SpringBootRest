package com.algafoodapi.error;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class ValidateError {

    @SerializedName(value = "Code")
    private String Code;
    @SerializedName(value = "Id")
    private String Id;
    @SerializedName(value = "Message")
    private String Message;
    @SerializedName(value = "MessageFront")
    private String MessageFront;
    @SerializedName(value = "CodeFront")
    private String CodeFront;
}
