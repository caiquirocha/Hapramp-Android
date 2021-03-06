package com.hapramp.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hapramp.R;
import com.hapramp.preferences.HaprampPreferenceManager;
import com.hapramp.steem.UserProfileFetcher;
import com.hapramp.steem.models.user.User;
import com.hapramp.ui.activity.AccountHistoryActivity;
import com.hapramp.utils.ImageHandler;
import com.hapramp.utils.ReputationCalc;
import com.hapramp.views.extraa.BubbleProgressBar;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import hapramp.walletinfo.Wallet;
import xute.cryptocoinview.CoinView;
import xute.cryptocoinview.Coins;

public class EarningFragment extends Fragment implements Wallet.UserAccountFieldsCallback, UserProfileFetcher.UserProfileFetchCallback {

  //------
  public static final String ARG_USERNAME = "username";
  @BindView(R.id.user_image)
  ImageView userImage;
  @BindView(R.id.username)
  TextView username;
  @BindView(R.id.user_fullname)
  TextView userFullname;
  @BindView(R.id.user_reputation)
  TextView userReputation;
  @BindView(R.id.user_fullname_container)
  LinearLayout userFullnameContainer;
  @BindView(R.id.see_history_btn)
  TextView seeHistoryBtn;
  @BindView(R.id.steem_icon)
  ImageView steemIcon;
  @BindView(R.id.divider1)
  ImageView divider1;
  @BindView(R.id.steem_power_icon)
  ImageView steemPowerIcon;
  @BindView(R.id.divider2)
  ImageView divider2;
  @BindView(R.id.steem_dollar_icon)
  ImageView steemDollarIcon;
  @BindView(R.id.divider3)
  ImageView divider3;
  @BindView(R.id.steem_saving_icon)
  ImageView steemSavingIcon;
  @BindView(R.id.divider4)
  ImageView divider4;
  @BindView(R.id.estimated_value)
  ImageView estimatedValue;
  @BindView(R.id.wallet_steem_tv)
  TextView walletSteemTv;
  @BindView(R.id.wallet_steem_power_tv)
  TextView walletSteemPowerTv;
  @BindView(R.id.wallet_steem_dollar_tv)
  TextView walletSteemDollarTv;
  @BindView(R.id.wallet_saving_tv)
  TextView walletSavingTv;
  @BindView(R.id.wallet_est_account_value_tv)
  TextView walletEstAccountValueTv;
  @BindView(R.id.steem_progress)
  BubbleProgressBar steemProgress;
  @BindView(R.id.steem_power_progress)
  BubbleProgressBar steemPowerProgress;
  @BindView(R.id.steem_dollar_progress)
  BubbleProgressBar steemDollarProgress;
  @BindView(R.id.saving_progress)
  BubbleProgressBar savingProgress;
  @BindView(R.id.estimated_value_progress)
  BubbleProgressBar estimatedValueProgress;
  @BindView(R.id.infor_card)
  CardView inforCard;
  @BindView(R.id.sbd_rate)
  CoinView sbdRateView;
  @BindView(R.id.steem_rate)
  CoinView steemRate;
  private Handler mHandler;
  private Wallet wallet;
  private Unbinder unbinder;
  private String mUsername;
  private Context mContext;
  private UserProfileFetcher userProfileFetcher;
  //------
  private double steem;
  private double sbd;
  private double sp;
  private double sbd_rate;
  private double steem_rate;

  public EarningFragment() {
    mHandler = new Handler();
    wallet = new Wallet();
    userProfileFetcher = new UserProfileFetcher();
    userProfileFetcher.setUserProfileFetchCallback(this);
    wallet.setUserAccountFieldsCallback(this);
  }

  @Override
  public void onAttach(Context context) {
    this.mContext = context;
    super.onAttach(context);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_earning, container, false);
    if (getArguments() != null) {
      mUsername = getArguments().getString(ARG_USERNAME);
    } else {
      mUsername = HaprampPreferenceManager.getInstance().getCurrentSteemUsername();
    }
    unbinder = ButterKnife.bind(this, view);
    steemRate.setCoinId(Coins.STEEM);
    steemRate.setRateCallback(new CoinView.RateCallback() {
      @Override
      public void onRate(double rate) {
        steem_rate = rate;
      }
    });
    sbdRateView.setCoinId(Coins.SBD);
    sbdRateView.setRateCallback(new CoinView.RateCallback() {
      @Override
      public void onRate(double rate) {
        sbd_rate = rate;
      }
    });
    fetchUserInfo();
    fetchWalletInfo();
    attachListener();
    return view;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  private void fetchUserInfo() {
    userProfileFetcher.fetchUserProfileFor(mUsername);
  }

  private void fetchWalletInfo() {
    wallet.requestUserAccount(mUsername);
  }

  private void attachListener() {
    seeHistoryBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(mContext, AccountHistoryActivity.class);
        intent.putExtra(AccountHistoryActivity.EXTRA_USERNAME, mUsername);
        mContext.startActivity(intent);
      }
    });
  }

  @Override
  public void onUserSteem(final String steem) {
    mHandler.post(new Runnable() {
      @Override
      public void run() {
        try {
          steemProgress.setVisibility(View.GONE);
          walletSteemTv.setText(steem);
        }
        catch (Exception e) {
        }
      }
    });
  }

  @Override
  public void onUserSteemDollar(final String dollar) {
    mHandler.post(new Runnable() {
      @Override
      public void run() {
        try {
          steemDollarProgress.setVisibility(View.GONE);
          walletSteemDollarTv.setText(dollar);
        }
        catch (Exception e) {
        }
      }
    });
  }

  @Override
  public void onUserSteemPower(final String steemPower) {
    mHandler.post(new Runnable() {
      @Override
      public void run() {
        try {
          steemPowerProgress.setVisibility(View.GONE);
          walletSteemPowerTv.setText(steemPower);
        }
        catch (Exception e) {
        }
      }
    });
  }

  @Override
  public void onUserSavingSteem(final String savingSteem) {
    mHandler.post(new Runnable() {
      @Override
      public void run() {
        try {
          savingProgress.setVisibility(View.GONE);
          walletSavingTv.append(savingSteem + ", ");
        }
        catch (Exception e) {
        }
      }
    });
  }

  @Override
  public void onUserSavingSBD(final String savingSBD) {
    mHandler.post(new Runnable() {
      @Override
      public void run() {
        try {
          savingProgress.setVisibility(View.GONE);
          walletSavingTv.append(savingSBD + " ");
        }
        catch (Exception e) {
        }
      }
    });
  }

  @Override
  public void onUserEstimatedAccountValue(final String value) {
    mHandler.post(new Runnable() {
      @Override
      public void run() {
        try {
          estimatedValueProgress.setVisibility(View.GONE);
          walletEstAccountValueTv.setText(value);
        }
        catch (Exception e) {
        }
      }
    });
  }

  @Override
  public void onUsdRates(final double sbd_usd, final double steem_usd) {
  }

  @Override
  public void onError(String error) {

  }

  @Override
  public void onUserFetched(User user) {
    bindData(user);
  }

  private void bindData(User data) {
    try {
      if (data != null) {
        username.setText(mUsername);
        userFullname.setText(data.getFullname());
        long rawReputation = Long.valueOf(data.getReputation());
        userReputation.setText(String.format(Locale.US, "(%.2f)", ReputationCalc.calculateReputation(rawReputation)));
        String profile_pic = String.format(getResources().getString(R.string.steem_user_profile_pic_format_large), mUsername);
        ImageHandler.loadCircularImage(mContext, userImage, profile_pic);
      }
    }
    catch (Exception e) {
    }
  }

  @Override
  public void onUserFetchError(String e) {

  }
}
