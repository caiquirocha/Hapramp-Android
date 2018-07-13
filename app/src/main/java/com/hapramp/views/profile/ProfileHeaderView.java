package com.hapramp.views.profile;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
import com.hapramp.R;
import com.hapramp.search.FollowCountManager;
import com.hapramp.search.FollowingSearchManager;
import com.hapramp.steem.UserProfileFetcher;
import com.hapramp.steem.models.user.User;
import com.hapramp.steemconnect.SteemConnectUtils;
import com.hapramp.steemconnect4j.SteemConnect;
import com.hapramp.steemconnect4j.SteemConnectCallback;
import com.hapramp.steemconnect4j.SteemConnectException;
import com.hapramp.ui.activity.ProfileEditActivity;
import com.hapramp.api.RetrofitServiceGenerator;
import com.hapramp.datamodels.CommunityModel;
import com.hapramp.datamodels.response.UserModel;
import com.hapramp.preferences.HaprampPreferenceManager;
import com.hapramp.steem.CommunityListWrapper;
import com.hapramp.steem.models.user.SteemUser;
import com.hapramp.ui.activity.WalletActivity;
import com.hapramp.utils.ImageHandler;
import com.hapramp.views.skills.InterestsView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import hapramp.walletinfo.Wallet;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
	* Created by Ankit on 12/30/2017.
	*/

public class ProfileHeaderView extends FrameLayout implements FollowCountManager.FollowCountCallback, FollowingSearchManager.FollowingSearchCallback, UserProfileFetcher.UserProfileFetchCallback {
		@BindView(R.id.profile_wall_pic)
		ImageView profileWallPic;
		@BindView(R.id.profile_pic)
		ImageView profilePic;
		@BindView(R.id.profile_header_container)
		RelativeLayout profileHeaderContainer;
		@BindView(R.id.usernameTv)
		TextView usernameTv;
		@BindView(R.id.hapname)
		TextView hapname;
		@BindView(R.id.profile_user_name_container)
		RelativeLayout profileUserNameContainer;
		@BindView(R.id.edit_btn)
		TextView editBtn;
		@BindView(R.id.follow_btn)
		TextView followBtn;
		@BindView(R.id.followUnfollowProgress)
		ProgressBar followUnfollowProgress;
		@BindView(R.id.bio)
		TextView bio;
		@BindView(R.id.divider_top)
		FrameLayout dividerTop;
		@BindView(R.id.post_counts)
		TextView postCounts;
		@BindView(R.id.followers_count)
		TextView followersCount;
		@BindView(R.id.followings_count)
		TextView followingsCount;
		@BindView(R.id.post_stats)
		LinearLayout postStats;
		@BindView(R.id.divider_bottom)
		FrameLayout dividerBottom;
		@BindView(R.id.interestCaption)
		TextView interestCaption;
		@BindView(R.id.interestsView)
		InterestsView interestsView;
		@BindView(R.id.postsCaption)
		TextView postsCaption;
		@BindView(R.id.wallet_info)
		TextView walletInfoBtn;
		@BindView(R.id.profile_header_view_real)
		RelativeLayout profileHeaderViewReal;
		@BindView(R.id.shimmer_view_container)
		ShimmerFrameLayout shimmerFrameLayout;

		private Context mContext;
		private String TICK_TEXT = "\u2713";
		private String mUsername;
		private Handler mHandler;
		private boolean loaded;
		private boolean isFollowed;
		private String me;
		private FollowCountManager followCountManager;
		private FollowingSearchManager followingSearchManager;
		private SteemConnect steemConnect;
		UserProfileFetcher userProfileFetcher;

		public ProfileHeaderView(@NonNull Context context) {
				super(context);
				init(context);
		}

		public ProfileHeaderView(@NonNull Context context, @Nullable AttributeSet attrs) {
				super(context, attrs);
				init(context);
		}

		public ProfileHeaderView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
				super(context, attrs, defStyleAttr);
				init(context);
		}

		private void init(Context context) {
				mContext = context;
				View view = LayoutInflater.from(context).inflate(R.layout.profile_header_view, this);
				ButterKnife.bind(this, view);
				mHandler = new Handler();
				attachListeners();
				userProfileFetcher = new UserProfileFetcher();
				userProfileFetcher.setUserProfileFetchCallback(this);
				me = HaprampPreferenceManager.getInstance().getCurrentSteemUsername();
				followCountManager = new FollowCountManager(this);
				followingSearchManager = new FollowingSearchManager(this);
				steemConnect = SteemConnectUtils.getSteemConnectInstance(HaprampPreferenceManager.getInstance().getSC2AccessToken());
		}

		private void attachListeners() {
				followBtn.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
								if (isFollowed) {
										confirmUnfollowAction();
								} else {
										requestFollowOnSteem();
								}
						}
				});

				walletInfoBtn.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View view) {
								Intent intent = new Intent(mContext, WalletActivity.class);
								intent.putExtra(WalletActivity.EXTRA_USERNAME, mUsername);
								mContext.startActivity(intent);
						}
				});
		}

		public void setUsername(String username) {
				this.mUsername = username;
				//hide shimmer
				if (shimmerFrameLayout != null) {
						shimmerFrameLayout.setBaseAlpha(0.1f);
						shimmerFrameLayout.setDropoff(0.08f);
						shimmerFrameLayout.setTilt(0f);
						shimmerFrameLayout.setDuration(2000);
						shimmerFrameLayout.setIntensity(0.02f);
						shimmerFrameLayout.setVisibility(VISIBLE);
						shimmerFrameLayout.startShimmerAnimation();
				}

				if (username != null) {
						if (!loaded) {
								checkCacheAndLoad();
								fetchUserInfo();
						}
				}
		}

		private void checkCacheAndLoad() {
				String json = HaprampPreferenceManager.getInstance().getUserProfile(mUsername);
				if (json.length() > 0) {
						User steemUser = new Gson().fromJson(json, User.class);
						bind(steemUser);
				}
		}

		private void fetchUserInfo() {
				Log.d("ProfileHeaderView", "Fetching user info for " + mUsername);
				followingSearchManager.requestFollowings(me);
				followCountManager.requestFollowInfo(mUsername);
				userProfileFetcher.fetchUserProfileFor(mUsername);
		}

		private void cacheUserProfile(User user) {
				String json = new Gson().toJson(user);
				HaprampPreferenceManager.getInstance().saveUserProfile(mUsername, json);
		}

		private void failedToFetchSteemInfo() {

		}

		private void bind(User data) {
				//hide shimmer
				if (shimmerFrameLayout != null) {
						shimmerFrameLayout.stopShimmerAnimation();
						shimmerFrameLayout.setVisibility(GONE);
				}
				//show content
				if (profileHeaderViewReal != null) {
						profileHeaderViewReal.setVisibility(VISIBLE);
				}
				//check for null view(incase view is removed)
				if (usernameTv == null)
						return;
				loaded = true;
				String profile_pic = String.format(getResources().getString(R.string.steem_user_profile_pic_format_large), mUsername);
				String wall_pic_url = data.getCover_image().length() > 0 ? data.getCover_image()
						:
						mContext.getResources().getString(R.string.default_wall_pic);
				String _bio = data.getAbout();
				ImageHandler.loadCircularImage(mContext, profilePic, profile_pic);
				ImageHandler.load(mContext, profileWallPic, wall_pic_url);
				usernameTv.setText(data.getFullname());
				hapname.setText(String.format("@%s", data.getUsername()));
				bio.setText(_bio);
				setPostsCount(data.getPostCount());
				if (mUsername.equals(HaprampPreferenceManager.getInstance().getCurrentSteemUsername())) {
						//self Profile
						followBtn.setVisibility(GONE);
						editBtn.setVisibility(VISIBLE);
						editBtn.setEnabled(false);
						CommunityListWrapper listWrapper = new Gson().fromJson(HaprampPreferenceManager.getInstance().getUserSelectedCommunityAsJson(), CommunityListWrapper.class);
						interestsView.setCommunities(listWrapper.getCommunityModels());
				} else {
						followBtn.setVisibility(VISIBLE);
						editBtn.setVisibility(GONE);
						// set follow or unfollow button
						invalidateFollowButton();
						fetchUserCommunities();
				}
		}

		private void invalidateFollowButton() {
				Set<String> followings = HaprampPreferenceManager.getInstance().getFollowingsSet();
				setFollowState(followings.contains(mUsername));
		}

		private void fetchUserCommunities() {

				RetrofitServiceGenerator.getService().getUserFromUsername(mUsername).enqueue(new Callback<UserModel>() {
						@Override
						public void onResponse(Call<UserModel> call, Response<UserModel> response) {
								if (response.isSuccessful()) {
										setCommunities(response.body().getCommunityModels());
								} else {
										setCommunities(new ArrayList<CommunityModel>());
								}
						}

						@Override
						public void onFailure(Call<UserModel> call, Throwable t) {
								setCommunities(new ArrayList<CommunityModel>());
						}
				});

		}

		private void setCommunities(List<CommunityModel> communities) {
				interestsView.setCommunities(communities);
		}

		private void requestFollowOnSteem() {
				showFollowProgress(true);
				new Thread() {
						@Override
						public void run() {
								steemConnect.follow(
										me,
										mUsername,
										new SteemConnectCallback() {
												@Override
												public void onResponse(String s) {
														mHandler.post(new Runnable() {
																@Override
																public void run() {
																		showFollowProgress(false);
																		userFollowedOnSteem();
																}
														});
												}

												@Override
												public void onError(SteemConnectException e) {
														Log.d("FollowError ", e.toString());
														mHandler.post(new Runnable() {
																@Override
																public void run() {
																		showFollowProgress(false);
																		userFollowFailed();
																}
														});
												}
										}
								);
						}
				}.start();
		}

		private void requestUnFollowOnSteem() {
				showFollowProgress(true);
				new Thread() {
						@Override
						public void run() {
								steemConnect.unfollow(
										me,
										mUsername,
										new SteemConnectCallback() {
												@Override
												public void onResponse(String s) {
														mHandler.post(new Runnable() {
																@Override
																public void run() {
																		showFollowProgress(false);
																		userUnFollowedOnSteem();
																}
														});
												}

												@Override
												public void onError(SteemConnectException e) {
														Log.d("UnfollowError ", e.toString());
														mHandler.post(new Runnable() {
																@Override
																public void run() {
																		showFollowProgress(false);
																		userUnfollowFailed();
																}
														});
												}
										}
								);
						}
				}.start();
		}

		private void confirmUnfollowAction() {
				AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
						.setTitle("Unfollow")
						.setMessage("Do you want to Unfollow " + mUsername + " ?")
						.setPositiveButton("UnFollow", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
										requestUnFollowOnSteem();
								}
						})
						.setNegativeButton("No", null);
				builder.show();
		}

		private void userFollowedOnSteem() {
				showFollowProgress(false);
				setFollowState(true);
				syncFollowings();
				t("You started following " + mUsername);
		}

		private void syncFollowings() {
				followingSearchManager.requestFollowings(me);
		}

		private void userFollowFailed() {
				showFollowProgress(false);
				setFollowState(false);
				t("Failed to follow " + mUsername);
		}

		private void userUnFollowedOnSteem() {
				showFollowProgress(false);
				setFollowState(false);
				syncFollowings();
				t("You unfollowed " + mUsername);
		}

		private void userUnfollowFailed() {
				showFollowProgress(false);
				setFollowState(true);
				t("Failed to unfollow " + mUsername);
		}

		private void showFollowProgress(boolean show) {
				if (show) {
						//hide button
						followBtn.setVisibility(GONE);
						followUnfollowProgress.setVisibility(VISIBLE);
				} else {
						//show button
						followBtn.setVisibility(VISIBLE);
						followUnfollowProgress.setVisibility(GONE);
				}
		}

		private void t(String s) {
				Toast.makeText(mContext, s, Toast.LENGTH_LONG).show();
		}

		private void navigateToProfileEditActivity() {
				Intent intent = new Intent(mContext, ProfileEditActivity.class);
				mContext.startActivity(intent);
		}

		private void setFollowState(boolean state) {
				if (state) {
						followBtn.setText(TICK_TEXT + " Following");
						isFollowed = true;
				} else {
						followBtn.setText("Follow");
						isFollowed = false;
				}
		}

		public void setPostsCount(long count) {
				postCounts.setText(String.format(mContext.getResources().getString(R.string.profile_posts_count_caption), count));
		}

		@Override
		public void onFollowInfo(final int follower, final int followings) {
				mHandler.post(new Runnable() {
						@Override
						public void run() {
								followingsCount.setText(String.format(mContext.getResources().getString(R.string.profile_following_count_caption),
										followings));
								followersCount.setText(String.format(mContext.getResources().getString(R.string.profile_followers_caption),
										follower));
						}
				});
		}

		@Override
		public void onFollowInfoError(String e) {

		}

		@Override
		public void onFollowingResponse(ArrayList<String> followings) {
				Log.d("Followings", followings.toString());
				HaprampPreferenceManager.getInstance().saveCurrentUserFollowings(followings);
		}

		@Override
		public void onFollowingRequestError(String e) {

		}

		@Override
		public void onUserFetched(User user) {
				Log.d("ProfileHeaderView", "Response " + user.toString());
				bind(user);
				cacheUserProfile(user);
		}

		@Override
		public void onUserFetchError(String e) {
				Log.d("ProfileHeaderView", "Error Fetching user info for " + mUsername);
				failedToFetchSteemInfo();
		}
}
