package com.pentarex.fh.api;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.pentarex.fh.api.beans.ExamBean;
import com.pentarex.fh.api.beans.MarksBean;
import com.pentarex.fh.api.beans.NewsBean;
import com.pentarex.fh.api.beans.ScheduleBean;

public class FHServicesImpl implements FHServices{
	private final String FH_URL = "https://ws.fh-joanneum.at";
    private final String API_KEY = "oZh7Nbxkej";

    @Override
    public List<String> getExamNotes(double titleId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isValidUser(String username, String password) {
        boolean valid = false;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        String url = FH_URL + "/getmarks.php?u=" + username + "&p=" + password + "&k=" + API_KEY;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(url);
            NodeList list = doc.getElementsByTagName("Status");
            String status = list.item(0).getTextContent();
            valid = list.item(0).getTextContent().equals("OK");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return valid;
    }

    @Override
    public List<ExamBean> getExams(String username, String password) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ScheduleBean> getSchedule(String course, int year) {
    	List<ScheduleBean> scheduleList = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        //String url = FH_URL + "/getnews.php?k=" + API_KEY;
        String url = "https://ws.fh-joanneum.at/getschedule.php?c=" + course +"&y=" + year +"&k=" + API_KEY;
        System.out.println(url);
        try{
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(url);
            NodeList events = doc.getElementsByTagName("Event");
            for(int i = 0; i < events.getLength(); i++){
            	ScheduleBean schedule = new ScheduleBean();
            	NodeList childItems = events.item(i).getChildNodes();
            	for(int j = 0; j < childItems.getLength(); j++){
            		String content = childItems.item(j).getTextContent();
                    switch(childItems.item(j).getNodeName()){
                        case "Title":
                        	schedule.setCourse(content);
                            break;
                        case "Lecturer":
                        	schedule.setLecturer(content.trim());
                            break;
                        case "Location":
                        	schedule.setLocation(content.trim());
                            break;
                        case "Type":
                        	schedule.setType(content.trim());
                            break;
                        case "Start":
                        	schedule.setStart(content.trim());
                            break;
                        case "End":
                        	schedule.setEnd(content.trim());
                            break;
                        default:
                            break;
                    }
            	}
            	scheduleList.add(schedule);
            }
        } catch(Exception e){
        	e.printStackTrace();
        }
        return scheduleList;
    }

    @Override
	public Multimap<String, MarksBean> getMarks(String username, String password) {
		Multimap<String, MarksBean> marksMap = ArrayListMultimap.create();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		String url = "https://ws.fh-joanneum.at/getmarks.php?u=" + username + "&p=" + password + "&k=" + API_KEY;
		try {
			builder = factory.newDocumentBuilder();
			Document doc = builder.parse(url);
			NodeList terms = doc.getElementsByTagName("Term");
			for (int i = 0; i < terms.getLength(); i++) {
				String semester = terms.item(i).getAttributes().getNamedItem("name").getNodeValue();
				NodeList items = terms.item(i).getChildNodes();
				for (int j = 0; j < items.getLength(); j++) {
					MarksBean marks = new MarksBean();
					NodeList childItems = items.item(j).getChildNodes();
					for(int x = 0; x < childItems.getLength(); x++){
						String content = childItems.item(x).getTextContent();
						switch (childItems.item(x).getNodeName()) {
						case "Title":
							marks.setTitle(content);
							break;
						case "Grade":
							marks.setGrade(Integer.parseInt(content));
							break;
						case "GradeWords":
							marks.setGradeWords(content);
							break;
						case "MarkId":
							marks.setGradeWords(content);
							break;
						default:
							break;
						}
					}
					marksMap.put(semester, marks);
				}
				
			}
		} catch (Exception e) {

		}
		return marksMap;
	}

    @Override
	public List<NewsBean> getNews() {
        List<NewsBean> news = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        //String url = FH_URL + "/getnews.php?k=" + API_KEY;
        String url = "http://fh-joanneum.at/aw/home/Info/News_Events/~bse/News/?art=RSS";
        try{
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(url);
            NodeList items = doc.getElementsByTagName("item");
            for(int i = 0; i < items.getLength(); i++){
                NewsBean newsBean = new NewsBean();
                NodeList childItems = items.item(i).getChildNodes();
                for(int j = 0; j < childItems.getLength(); j++){
                    String content = childItems.item(j).getTextContent();
                    switch(childItems.item(j).getNodeName()){
                        case "title":
                            newsBean.setTitle(content);
                            break;
                        case "link":
                            newsBean.setLink(content.trim());
                            break;
                        case "description":
                            newsBean.setDescription(content.trim());
                            break;
                        default:
                            break;
                    }
                }
                news.add(newsBean);
            }
        } catch(Exception e){
            //e.printStackTrace();
        }
        return news;
    }
    
}
