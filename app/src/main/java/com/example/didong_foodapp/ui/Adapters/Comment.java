package com.example.didong_foodapp.ui.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.didong_foodapp.R;
import com.example.didong_foodapp.ui.Models.CommentModel;

import java.util.List;

public class Comment extends RecyclerView.Adapter<Comment.ViewHolder> {
    Context context;
    int layout;
    List<CommentModel> commentModelList;

    public Comment(Context context, int layout, List<CommentModel> commentModelList) {
        this.context = context;
        this.layout = layout;
        this.commentModelList = commentModelList;
    }

    public class ViewHolder  extends RecyclerView.ViewHolder{
        TextView txtCommentTitle,txtCommentContent,txtScore;
        public ViewHolder(View itemView){

            super(itemView);
            txtScore=itemView.findViewById(R.id.scoreTxtComment);
            txtCommentTitle=itemView.findViewById(R.id.titleTxtComment);
            txtCommentContent=itemView.findViewById(R.id.contentTxtComment);

        }
    }

    @NonNull
    @Override
    public Comment.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(layout,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Comment.ViewHolder holder, int position) {
        CommentModel comModel=commentModelList.get(position);
        holder.txtCommentTitle.setText(comModel.getTitle());
        holder.txtCommentContent.setText(comModel.getContent());
        holder.txtScore.setText(comModel.getScore()+"");

    }

    @Override
    public int getItemCount() {
        int comments=commentModelList.size();
        if (comments>5)
            return 5;
        else
            return commentModelList.size();
    }


}
