package com.fan.eightrestaurant.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xdkj.campus.menu.R;
import com.fan.eightrestaurant.utils.PathUtils;
import com.fan.eightrestaurant.utils.UpLoadUtils;
import com.xdkj.campus.menu.api.APIAddr;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import okhttp3.Call;

/**
 * 用户上传头像，修改昵称，退出登录
 */
public class UpLoadActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView upLoadNickName;
    private SimpleDraweeView simpleDraweeView;

    private Button exitBtn;
    private TextView UpLoadImage, sendBirthday, upLoadRealname, upLoadEmail, showBirthday,
            showRealname, showEmail, showNiceName;
    String updateNickName, updateRealName, updateBirthday, upateEmail, updateImagePath;

    /**
     * 选择本地照片
     */
    protected static final int CHOOSE_PICTURE = 0;
    /**
     * 拍照
     */
    protected static final int TAKE_PICTURE = 1;
    /**
     * 裁剪图片
     */
    private static final int CROP_SMALL_PICTURE = 2;
    protected static Uri tempUri;
    private SharedPreferences getInfo;
    private String user_id, nickname, imagePath, token, secretkey, realname, birthday, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_load);
        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        ((TextView) findViewById(R.id.title_middle)).setText("编辑资料");
        findViewById(R.id.title_ll_left).setOnClickListener(new View
                .OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        simpleDraweeView = (SimpleDraweeView) findViewById(R.id.activity_up_load_show_headimage);
        upLoadNickName = (TextView) findViewById(R.id.activity_up_load_name);
        UpLoadImage = (TextView) findViewById(R.id.activity_up_load_show_image);
        sendBirthday = (TextView) findViewById(R.id.activity_up_load_birthday);
        upLoadRealname = (TextView) findViewById(R.id.activity_up_load_realname);
        upLoadEmail = (TextView) findViewById(R.id.activity_up_load_email);
        showBirthday = (TextView) findViewById(R.id.activity_up_load_show_birthday);
        showRealname = (TextView) findViewById(R.id.activity_up_load_show_realname);
        showEmail = (TextView) findViewById(R.id.activity_up_load_show_email);
        showNiceName = (TextView) findViewById(R.id.activity_up_load_show_name);
//        exitBtn = (Button) findViewById(R.id.activity_eixt_button);

        getInfo = getSharedPreferences("saveSelfInfo", Context.MODE_PRIVATE);
        user_id = getInfo.getString("user_id", user_id);
        nickname = getInfo.getString("nickname", nickname);
        imagePath = getInfo.getString("imagePath", imagePath);
        token = getInfo.getString("token", token);
        secretkey = getInfo.getString("secretkey", secretkey);
        realname = getInfo.getString("realname", realname);
        birthday = getInfo.getString("birthday", birthday);
        email = getInfo.getString("email", email);

        showNiceName.setText(nickname);
        simpleDraweeView.setImageURI(imagePath);
        showRealname.setText(realname);
        showEmail.setText(email);
        showBirthday.setText(birthday);


        userheadRl = (RelativeLayout) findViewById(R.id.userhead);
        nicknameRl = (RelativeLayout) findViewById(R.id.nickname);
        realnameRl = (RelativeLayout) findViewById(R.id.realname);
        birthdayRl = (RelativeLayout) findViewById(R.id.birthday);
        emailRl = (RelativeLayout) findViewById(R.id.email);

        userheadRl.setOnClickListener(this);
        nicknameRl.setOnClickListener(this);
        realnameRl.setOnClickListener(this);
        birthdayRl.setOnClickListener(this);
        emailRl.setOnClickListener(this);

//        exitBtn.setOnClickListener(this);
    }

    RelativeLayout userheadRl;
    RelativeLayout nicknameRl;
    RelativeLayout realnameRl;
    RelativeLayout birthdayRl;
    RelativeLayout emailRl;

    /**
     * 控件的点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.userhead:
                showChoosePicDialog();
                break;
            case R.id.nickname:
                showUpdateNickNameDiaLog();
                break;
            case R.id.realname:
                showUpdateRealNameDiaLog();
                break;
            case R.id.birthday:
                showUpdateBirthdayDiaLog();
                break;
            case R.id.email:
                showUpdateEmaiDiaLog();
                break;

//            case R.id.activity_eixt_button:
//                OkHttpUtils.post().url(PathUtils.postLoginOutUrl())
//                        .addParams("token", token)
//                        .addParams("secretkey", secretkey)
//                        .build()
//                        .execute(new StringCallback()
//                        {
//                            @Override
//                            public void onError(Call call, Exception e)
//                            {
//                                Log.e("AAA", "网络数据下载失败 ");
//                            }
//
//                            @Override
//                            public void onResponse(String response)
//                            {
//                                Log.e("AAA", "网络数据下载成功 " + response);
//                                try
//                                {
//                                    JSONObject jb = new JSONObject(response);
//                                    String message = jb.getString("message");
//                                    if (message.equals("TokenError"))
//                                    {
//                                        AlertDialog.Builder builder = new AlertDialog.Builder
//                                                (UpLoadActivity.this);
//                                        builder.setTitle("提示");
//                                        builder.setMessage("登录异常，请重新登录");
//                                        builder.setNegativeButton("确定", new DialogInterface
//                                                .OnClickListener()
//                                        {
//                                            @Override
//                                            public void onClick(DialogInterface dialog, int which)
//                                            {
//                                                Intent intent = new Intent(UpLoadActivity.this,
//                                                        LoginActivity.class);
//                                                startActivity(intent);
//                                                finish();
//                                            }
//                                        });
//                                        builder.create().show();
//                                    } else if (message.equals("ok"))
//                                    {
//                                        Intent intent = new Intent(UpLoadActivity.this,
//                                                LoginActivity.class);
//                                        startActivity(intent);
//                                    } else if (message.equals("eroor"))
//                                    {
//                                        Toast.makeText(UpLoadActivity.this, "用户注销登录失败", Toast
//                                                .LENGTH_SHORT).show();
//                                    }
//                                } catch (JSONException e)
//                                {
//                                    e.printStackTrace();
//                                }
//                            }
//                        });
//
//                break;
        }
    }

    /**
     * 显示修改昵称的编辑框
     */
    private void showUpdateNickNameDiaLog() {
        final EditText editText = new EditText(UpLoadActivity.this);
        AlertDialog.Builder builder = new AlertDialog.Builder(UpLoadActivity.this);
        builder.setIcon(R.mipmap.icon);
        builder.setTitle("请输入昵称");
        builder.setView(editText);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (editText.getText().toString().equals("")) {
                    Toast.makeText(UpLoadActivity.this, "昵称不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    OkHttpUtils.post()
                            .url(PathUtils.postUpdateInformationUrl())
                            .addParams("id", user_id)
                            .addParams("nickname", editText.getText().toString())
                            .addParams("secretkey", secretkey)
                            .addParams("token", token)
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e) {
                                }

                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        String string = jsonObject.getString("message");
                                        if (string.equals("TokenError")) {
                                            AlertDialog.Builder builder = new AlertDialog.Builder
                                                    (UpLoadActivity.this);
                                            builder.setTitle("提示");
                                            builder.setMessage("登录异常，请重新登录");
                                            builder.setNegativeButton("确定", new DialogInterface
                                                    .OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int
                                                        which) {
                                                    Intent intent = new Intent(UpLoadActivity
                                                            .this, LoginActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            });
                                            builder.create().show();
                                        } else if (string.equals("ok")) {
                                            updateNickName = editText.getText().toString();
                                            updateUserInfo(updateNickName, realname, birthday, email, imagePath);
                                            showNiceName.setText(editText.getText().toString());
                                            Toast.makeText(UpLoadActivity.this, "信息保存成功", Toast
                                                    .LENGTH_SHORT).show();
                                        } else if (string.equals("error")) {
                                            Toast.makeText(UpLoadActivity.this, "信息保存失败", Toast
                                                    .LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                }
            }
        });
        builder.show();
    }

    /**
     * 显示修改真实姓名的编辑框
     */
    private void showUpdateRealNameDiaLog() {
        final EditText editText = new EditText(UpLoadActivity.this);
        AlertDialog.Builder builder = new AlertDialog.Builder(UpLoadActivity.this);
        builder.setIcon(R.mipmap.icon);
        builder.setTitle("请输入真实姓名");
        builder.setView(editText);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (editText.getText().toString().equals("")) {
                    Toast.makeText(UpLoadActivity.this, "姓名不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    OkHttpUtils.post()
                            .url(PathUtils.postUpdateInformationUrl())
                            .addParams("id", user_id)
                            .addParams("realname", editText.getText().toString())
                            .addParams("secretkey", secretkey)
                            .addParams("token", token)
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e) {
                                }

                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        String string = jsonObject.getString("message");
                                        if (string.equals("TokenError")) {
                                            AlertDialog.Builder builder = new AlertDialog.Builder
                                                    (UpLoadActivity.this);
                                            builder.setTitle("提示");
                                            builder.setMessage("登录异常，请重新登录");
                                            builder.setNegativeButton("确定", new DialogInterface
                                                    .OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int
                                                        which) {
                                                    Intent intent = new Intent(UpLoadActivity
                                                            .this, LoginActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            });
                                            builder.create().show();
                                        } else if (string.equals("ok")) {
                                            updateRealName = editText.getText().toString();
                                            updateUserInfo(nickname,updateRealName,birthday,email,imagePath);

                                            showRealname.setText(editText.getText().toString());
                                            Toast.makeText(UpLoadActivity.this, "信息保存成功", Toast
                                                    .LENGTH_SHORT).show();
                                        } else if (string.equals("error")) {
                                            Toast.makeText(UpLoadActivity.this, "信息保存失败", Toast
                                                    .LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                }
            }
        });
        builder.show();
    }

    /**
     * 显示生日的编辑框
     */
    private void showUpdateBirthdayDiaLog() {
        final EditText editText = new EditText(UpLoadActivity.this);
        AlertDialog.Builder builder = new AlertDialog.Builder(UpLoadActivity.this);
        builder.setIcon(R.mipmap.icon);
        builder.setTitle("请输入生日");
        builder.setView(editText);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (editText.getText().toString().equals("")) {
                    Toast.makeText(UpLoadActivity.this, "生日不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    OkHttpUtils.post()
                            .url(PathUtils.postUpdateInformationUrl())
                            .addParams("id", user_id)
                            .addParams("birthday", editText.getText().toString())
                            .addParams("secretkey", secretkey)
                            .addParams("token", token)
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e) {
                                }

                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        String string = jsonObject.getString("message");
                                        if (string.equals("TokenError")) {
                                            AlertDialog.Builder builder = new AlertDialog.Builder
                                                    (UpLoadActivity.this);
                                            builder.setTitle("提示");
                                            builder.setMessage("登录异常，请重新登录");
                                            builder.setNegativeButton("确定", new DialogInterface
                                                    .OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int
                                                        which) {
                                                    Intent intent = new Intent(UpLoadActivity
                                                            .this, LoginActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            });
                                            builder.create().show();
                                        } else if (string.equals("ok")) {
                                            updateBirthday = editText.getText().toString();
                                            updateUserInfo(nickname,realname,updateBirthday,email,imagePath);

                                            showBirthday.setText(editText.getText().toString());
                                            Toast.makeText(UpLoadActivity.this, "信息保存成功", Toast
                                                    .LENGTH_SHORT).show();
                                        } else if (string.equals("error")) {
                                            Toast.makeText(UpLoadActivity.this, "信息保存失败", Toast
                                                    .LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                }
            }
        });
        builder.show();
    }

    /**
     * 显示邮箱的编辑框
     */
    private void showUpdateEmaiDiaLog() {
        final EditText editText = new EditText(UpLoadActivity.this);
        AlertDialog.Builder builder = new AlertDialog.Builder(UpLoadActivity.this);
        builder.setIcon(R.mipmap.icon);
        builder.setTitle("请输入邮箱");
        builder.setView(editText);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (editText.getText().toString().equals("")) {
                    Toast.makeText(UpLoadActivity.this, "邮箱不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    OkHttpUtils.post()
                            .url(PathUtils.postUpdateInformationUrl())
                            .addParams("id", user_id)
                            .addParams("email", editText.getText().toString())
                            .addParams("secretkey", secretkey)
                            .addParams("token", token)
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e) {
                                }

                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        String string = jsonObject.getString("message");
                                        if (string.equals("TokenError")) {
                                            AlertDialog.Builder builder = new AlertDialog.Builder
                                                    (UpLoadActivity.this);
                                            builder.setTitle("提示");
                                            builder.setMessage("登录异常，请重新登录");
                                            builder.setNegativeButton("确定", new DialogInterface
                                                    .OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int
                                                        which) {
                                                    Intent intent = new Intent(UpLoadActivity
                                                            .this, LoginActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            });
                                            builder.create().show();
                                        } else if (string.equals("ok")) {
                                            upateEmail = editText.getText().toString();
                                            updateUserInfo(nickname,realname,birthday,email,imagePath);

                                            showEmail.setText(editText.getText().toString());
                                            Toast.makeText(UpLoadActivity.this, "信息保存成功", Toast
                                                    .LENGTH_SHORT).show();
                                        } else if (string.equals("error")) {
                                            Toast.makeText(UpLoadActivity.this, "信息保存失败", Toast
                                                    .LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                }
            }
        });
        builder.show();
    }

    /**
     * 显示修改头像的对话框
     */
    private void showChoosePicDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("设置头像");
        String[] items = {"选择本地照片", "拍照"};
        builder.setNegativeButton("取消", null);
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case CHOOSE_PICTURE: // 选择本地照片
                        Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                        openAlbumIntent.setType("image/*");
                        startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                        break;
                    case TAKE_PICTURE: // 拍照
                        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        tempUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory()
                                , "image.jpg"));
                        // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                        startActivityForResult(openCameraIntent, TAKE_PICTURE);
                        break;
                }
            }
        });
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { // 如果返回码是可以用的
            switch (requestCode) {
                case TAKE_PICTURE:
                    startPhotoZoom(tempUri); // 开始对图片进行裁剪处理
                    break;
                case CHOOSE_PICTURE:
                    startPhotoZoom(data.getData()); // 开始对图片进行裁剪处理
                    break;
                case CROP_SMALL_PICTURE:
                    if (data != null) {
                        setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
                    }
                    break;
            }
        }
    }

    /**
     * 裁剪图片
     *
     * @param uri
     */
    private void startPhotoZoom(Uri uri) {
        if (uri == null) {
            Toast.makeText(UpLoadActivity.this, "该图片的地址不存在", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }

    /**
     * 保存裁减之后的图片数据
     *
     * @param data
     */
    private void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            photo = UpLoadUtils.toRoundBitmap(photo, tempUri); // 这个时候的图片已经被处理成圆形的了
            uploadPic(photo);
        }
    }

    private void uploadPic(Bitmap photo) {
        String imageUrl = UpLoadUtils.savePhoto(photo, Environment
                .getExternalStorageDirectory().getAbsolutePath(), String
                .valueOf(System.currentTimeMillis()));
        File file = new File(imageUrl);
        if (imageUrl != null) {
            // 拿着imagePath上传了
            OkHttpUtils.post()
                    .url(PathUtils.postImageUploadUrl())
                    .addParams("id", user_id)
                    .addFile("file", file.getName(), file)
                    .addParams("secretkey", secretkey)
                    .addParams("token", token)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e) {
                            Log.e("AAA", "网络数据下载失败");
                        }

                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jb = new JSONObject(response);
                                String message = jb.getString("message");
                                String image = APIAddr.BASE_IMG_URL + jb.getString("value");

                                updateImagePath= APIAddr.BASE_IMG_URL+jb.getString("value");
                                updateUserInfo(nickname,realname,birthday,email,updateImagePath);


                                if (message.equals("TokenError")) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder
                                            (UpLoadActivity.this);
                                    builder.setTitle("提示");
                                    builder.setMessage("登录异常，请重新登录");
                                    builder.setNegativeButton("确定", new DialogInterface
                                            .OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(UpLoadActivity.this,
                                                    LoginActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                                    builder.create().show();
                                }
                                if (message.equals("uploadError")) {
                                    Toast.makeText(UpLoadActivity.this, "头像上传失败", Toast
                                            .LENGTH_SHORT).show();
                                } else if (message.equals("ok")) {
                                    simpleDraweeView.setImageURI(image);
                                    Toast.makeText(UpLoadActivity.this, "头像修改成功", Toast
                                            .LENGTH_SHORT).show();
                                } else if (message.equals("empty")) {
                                    Toast.makeText(UpLoadActivity.this, "图片地址不存在", Toast
                                            .LENGTH_SHORT).show();
                                } else if (message.equals("severError")) {
                                    Toast.makeText(UpLoadActivity.this, "服务器出错", Toast
                                            .LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                            }
                        }
                    });
        }
    }

    /**
     * 更新用户信息
     */
    private void updateUserInfo(String updateNickName,String updateRealName,String updateBirthday,
                                String upateEmail,String updateImagePath) {
        SharedPreferences.Editor editorSelfInfo = getInfo.edit();
        editorSelfInfo.putString("nickname",updateNickName);
        editorSelfInfo.putString("realname",updateRealName);
        editorSelfInfo.putString("birthday",updateBirthday);
        editorSelfInfo.putString("email",upateEmail);
        editorSelfInfo.putString("imagePath",updateImagePath);
        editorSelfInfo.commit();
    }


}
