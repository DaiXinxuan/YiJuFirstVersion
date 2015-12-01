package pictureconnectinit;

import testandmanage.MyApplication;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.example.haofangchan2.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class InitPicture_setting {

	public static void getImageLoader(String address,ImageView imageView) {
		//����ͼƬ���ô��ң��ӱ�־
		if(address!=null){
        imageView.setTag(address);
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.resetViewBeforeLoading(true).cacheOnDisk(true)
				.imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
				.displayer(new FadeInBitmapDisplayer(300)).build();

		ImageLoader loader = ImageLoader.getInstance();
		loader.init(ImageLoaderConfiguration.createDefault(MyApplication
				.getContext()));
        loader.displayImage(MyApplication.base_url_picture+address, imageView, options);}
	}

	public void init() {

		// ͼƬ��ȡ���£�
		ImageLoader imageLoader = ImageLoader.getInstance();
		DisplayImageOptions options;
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.default_ptr_rotate) // ����ͼƬ�������ڼ���ʾ��ͼƬ
				.showImageForEmptyUri(R.drawable.default_ptr_rotate)// ����ͼƬUriΪ�ջ��Ǵ����ʱ����ʾ��ͼƬ
				.showImageOnFail(R.drawable.default_ptr_rotate) // ����ͼƬ����/��������д���ʱ����ʾ��ͼƬ
				.cacheInMemory(true)// �������ص�ͼƬ�Ƿ񻺴����ڴ���
				.cacheOnDisc(true)// �������ص�ͼƬ�Ƿ񻺴���SD����
				.considerExifParams(true) // �Ƿ���JPEGͼ��EXIF��������ת����ת��
				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)// ����ͼƬ����εı��뷽ʽ��ʾ
				/*
				 * .bitmapConfig(Bitmap.Config.RGB_565)//����ͼƬ�Ľ�������//
				 *//*
					 * .decodingOptions(android.graphics.BitmapFactory.Options
					 * decodingOptions)//����ͼƬ�Ľ�������
					 */// .delayBeforeLoading(int delayInMillis)//int
						// delayInMillisΪ�����õ�����ǰ���ӳ�ʱ��
				// ����ͼƬ���뻺��ǰ����bitmap��������
				// .preProcessor(BitmapProcessor preProcessor)
				.resetViewBeforeLoading(true)// ����ͼƬ������ǰ�Ƿ����ã���λ
				.displayer(new RoundedBitmapDisplayer(20))// �Ƿ�����ΪԲ�ǣ�����Ϊ����
				.displayer(new FadeInBitmapDisplayer(100))// �Ƿ�ͼƬ���غú���Ķ���ʱ��
				.build();// �������
	}
}
