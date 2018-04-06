package eu.bittrade.libs.steemj.apis.follow.model;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import eu.bittrade.libs.steemj.apis.follow.enums.FollowType;
import eu.bittrade.libs.steemj.base.models.AccountName;

/**
 * This class represents a Steem "follow_api_object" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class FollowApiObject {
    private AccountName follower;
    private AccountName following;
    private List<FollowType> what;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    protected FollowApiObject() {
    }

    /**
     * @return The account which is following {@link #getFollowing()}.
     */
    public AccountName getFollower() {
        return follower;
    }

    /**
     * @return The account which is followed by {@link #getFollower()}.
     */
    public AccountName getFollowing() {
        return following;
    }

    /**
     * @return The variant of the follow (see
     *         {@link eu.bittrade.libs.steemj.apis.follow.enums.FollowType
     *         FollowType}.
     */
    public List<FollowType> getWhat() {
        return what;
    }

    @Override
    public String toString() {
        return "FollowApiObject{" +
                "follower=" + follower +
                ", following=" + following +
                ", what=" + what +
                '}';
    }

    //
//    @Override
//    public String toString() {
//        return ToStringBuilder.reflectionToString(this);
//    }
//
}
