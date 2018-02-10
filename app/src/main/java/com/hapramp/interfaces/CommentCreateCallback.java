package com.hapramp.interfaces;

import com.hapramp.models.response.CommentCreateResponse;

/**
 * Created by Ankit on 11/13/2017.
 */

public interface CommentCreateCallback {
    void onCommentCreated(CommentCreateResponse response);
    void onCommentCreateError();
}
