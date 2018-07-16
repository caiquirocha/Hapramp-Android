package com.hapramp.search.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FollowingsResponse {

  @Expose
  @SerializedName("id")
  public int id;
  @Expose
  @SerializedName("result")
  public Result result;
  @Expose
  @SerializedName("jsonrpc")
  public String jsonrpc;

  public FollowingsResponse(int id, Result result, String jsonrpc) {
    this.id = id;
    this.result = result;
    this.jsonrpc = jsonrpc;
  }

  public Result getResult() {
    return result;
  }

  public static class Result {
    @Expose
    @SerializedName("following")
    public List<Followings> followers;

    public List<Followings> getFollowings() {
      return followers;
    }
  }

  public static class Followings {
    @Expose
    @SerializedName("what")
    public List<String> what;
    @Expose
    @SerializedName("following")
    public String following;
    @Expose
    @SerializedName("follower")
    public String follower;

    public Followings(List<String> what, String following, String follower) {
      this.what = what;
      this.following = following;
      this.follower = follower;
    }

    public String getFollowing() {
      return following;
    }

  }

}
