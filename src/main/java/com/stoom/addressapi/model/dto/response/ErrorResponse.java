package com.stoom.addressapi.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(NON_EMPTY)
@ApiModel(description = "Represents all error responses in service.")
public class ErrorResponse implements Serializable {

    private static final long serialVersionUID = -6431868610599519417L;

    @ApiModelProperty("An important information about this error (Ex. field name).")
    private String error;
    @ApiModelProperty("Business Error code, that vary due to the context.")
    private String errorCode;
    @ApiModelProperty("A basic description about what happened.")
    private String errorDescription;

    public ErrorResponse tag(String tag) {
        this.error = tag;
        return this;
    }

    public ErrorResponse description(String description) {
        this.errorDescription = description;
        return this;
    }

    public static ErrorResponse as(String description) {
        return new ErrorResponse().description(description);
    }
}
