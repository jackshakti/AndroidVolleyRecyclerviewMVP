package shakti.volleymvprecyclerview.app.presenter.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import shakti.volleymvprecyclerview.R;
import shakti.volleymvprecyclerview.app.model.pojo.RecyclerviewPojo;

import static shakti.volleymvprecyclerview.library.utils.Utils.*;

/**
 * Created by Sakthivel on 15-12-2019.
 */
public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.MyViewHolder> {
    private List<RecyclerviewPojo> data;
    private Context context;

    public RecyclerviewAdapter(Context mContext, List<RecyclerviewPojo> recyclerViewList) {
      this.context = mContext;
      this.data = recyclerViewList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView Iv_projectOwner;
        private TextView Tv_successStoryTitle;
        private TextView Tv_projectDescLabel, Tv_projectDesc;

        public MyViewHolder(@NonNull View view) {
            super(view);
            Iv_projectOwner = view.findViewById(R.id.SuccessStory_single_projectOwner_imageView);
            Tv_successStoryTitle = view.findViewById(R.id.SuccessStory_single_title_textView);
            Tv_projectDescLabel = view.findViewById(R.id.SuccessStory_single_projectDescription_label);
            Tv_projectDesc = view.findViewById(R.id.SuccessStory_single_projectDescription_textView);
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.success_story_single_screen, parent, false);

        return new MyViewHolder(itemView);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        RecyclerviewPojo successStoryPojo = data.get(position);

        if (data.get(position).getsProjectImage().length() > 0) {
            Picasso.with(context)
                    .load(data.get(position).getsProjectImage())
                    .placeholder(R.drawable.ic_no_image)
                    .error(R.drawable.ic_no_image)
                    .into(holder.Iv_projectOwner);
        }

        holder.Tv_successStoryTitle.setText(checkEmpty(context, successStoryPojo.getProjectName()));
        holder.Tv_projectDesc.setText(checkEmpty(context, successStoryPojo.getsDescription()));

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.scale_up_fast);
        holder.itemView.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}