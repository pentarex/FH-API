package com.pentarex.fh.api;

import java.util.List;

import com.pentarex.fh.api.beans.ExamBean;
import com.pentarex.fh.api.beans.MarksBean;
import com.pentarex.fh.api.beans.NewsBean;
import com.pentarex.fh.api.beans.ScheduleBean;

public interface FHServices {
	public boolean isValidUser(String username, String password);
    public List<ExamBean> getExams(String username, String password);
    public List<ScheduleBean> getSchedule(String course, int year);
    public List<MarksBean> getMarks(String username, String password);
    public List<String> getExamNotes(double titleId);
    public List<NewsBean> getNews();
}
