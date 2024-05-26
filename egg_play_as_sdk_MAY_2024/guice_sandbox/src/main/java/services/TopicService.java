package services;

import models.*;

import javax.inject.Inject;
import java.util.*;

public class TopicService {

    @Inject
    public TopicService() {
    }

    public List<Topic> getTopics() {
        Topic topic = new Topic();
        topic.setName("computer science");
        List<Topic> topics = List.of(topic);
        return topics;
    }
}
