package com.pentarex.fh.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javafx.concurrent.Task;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.pentarex.fh.api.beans.FullNewsBean;
import com.pentarex.fh.api.beans.NewsBean;

public class NewsImplementation {
	private Document page = null;
	public Task<FullNewsBean> getArticle(NewsBean article){
	    Task<FullNewsBean> getNewsTask = new Task<FullNewsBean>() {
			@Override
			protected FullNewsBean call() throws Exception {
		        	FullNewsBean fnb = new FullNewsBean();
		        	fnb.setTitle(article.getTitle());
		        	fnb.setLink(article.getLink());
		        	try{
		        		page = Jsoup.connect(article.getLink()).get();
					} catch (IOException e){
						//TODO log4j
						e.printStackTrace();
					}
					if(page !=null){
						Elements intro = page.select("#text_page #intro");
						fnb.setDescription(intro.text());
						Elements article = page.select("#text_page p");
						fnb.setArticle(article.text());
						Element imageUrl = page.select("#col_right .cont a img").first();
						if(imageUrl != null) fnb.setImageUrl("www.fh-joanneum.at" + imageUrl.absUrl("src"));
					}
				return fnb;
			}
	    };
        return getNewsTask;
	}
	
	
	// This is for the second time... if the news are loaded once dont visit the website anymore
	// Implement it 
	public Task<List<FullNewsBean>> getNews(List<FullNewsBean> newsInDatabase){
		List<FullNewsBean> fullNews = new ArrayList<FullNewsBean>();
		Comparator<FullNewsBean> byTitleFnb = new Comparator<FullNewsBean>() {
            public int compare(FullNewsBean left, FullNewsBean right) {
            	return left.getTitle().compareTo(right.getTitle());
            }
        };
        Collections.sort(newsInDatabase, byTitleFnb);
		FHServicesImpl fhImpl = new FHServicesImpl();
		List<NewsBean> news = fhImpl.getNews();
		
		Comparator<NewsBean> byTitleNb = new Comparator<NewsBean>() {
            public int compare(NewsBean left, NewsBean right) {
            	return left.getTitle().compareTo(right.getTitle());
            }
        };
        Collections.sort(news, byTitleNb);
        
		List<NewsBean> newsToFetch = new ArrayList<>();
		if(newsInDatabase.size() == news.size()){
			for(int i = 0; i < newsInDatabase.size(); i++){
				String existingArticle = newsInDatabase.get(i).getTitle();
				String newArticle = news.get(i).getTitle();
				if(!existingArticle.equals(newArticle))newsToFetch.add(news.get(i));
			}
		}
		fullNews.addAll(newsInDatabase);
	    Task<List<FullNewsBean>> getNewsTask = new Task<List<FullNewsBean>>() {
			@Override
			protected List<FullNewsBean> call() throws Exception {
				for(NewsBean nb : newsToFetch){
		        	FullNewsBean fnb = new FullNewsBean();
		        	fnb.setTitle(nb.getTitle());
		        	fnb.setLink(nb.getLink());
		        	try{
		        		page = Jsoup.connect(nb.getLink()).get();
					} catch (IOException e){
						//TODO log4j
						e.printStackTrace();
					}
					if(page !=null){
						Elements intro = page.select("#text_page #intro");
						fnb.setDescription(intro.text());
						Elements article = page.select("#text_page p");
						fnb.setArticle(article.text());
						Element imageUrl = page.select("#col_right .cont a img").first();
						if(imageUrl != null) fnb.setImageUrl("www.fh-joanneum.at" + imageUrl.absUrl("src"));
					}
					fullNews.add(fnb);
		        }
				return fullNews;
			}
	    };
        return getNewsTask;
	}
}
