package com.boardAdmin.common.ui;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;


@Schema(description = "Response container for API responses")
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseContainer<T> implements Serializable {

    @Schema(description = "Status of the response", example = "success", requiredMode = Schema.RequiredMode.REQUIRED)
    public ResponseStatus status;

    @Nullable
    @Schema(description = "Response data")
    public T data;

    private ResponseContainer(@NonNull ResponseStatus status, @Nullable T data) {
        this.status = status;
        this.data = data;
    }

    @NonNull
    public static <T> ResponseContainer<T> success() {
        return new ResponseContainer<>(ResponseStatus.SUCCESS, null);
    }

    @NonNull
    public static <T> ResponseContainer<T> success(T data) {
        return new ResponseContainer<>(ResponseStatus.SUCCESS, data);
    }

    @NonNull
    public static <T> ResponseContainer<T> fail() {
        return new ResponseContainer<>(ResponseStatus.FAIL, null);
    }

    @NonNull
    public static <T> ResponseContainer<T> fail(T data) {
        return new ResponseContainer<>(ResponseStatus.FAIL, data);
    }

    @NonNull
    public static <T> ResponseContainer<T> error(T data) {
        return new ResponseContainer<>(ResponseStatus.ERROR, data);
    }
}