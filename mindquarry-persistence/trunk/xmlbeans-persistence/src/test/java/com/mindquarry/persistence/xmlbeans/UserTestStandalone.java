package com.mindquarry.persistence.xmlbeans;

import java.util.List;

import junit.framework.TestCase;

import com.mindquarry.common.persistence.Session;
import com.mindquarry.types.user.Email;
import com.mindquarry.types.user.User;

public class UserTestStandalone extends TestCase {

    public void testConversation() throws Exception {
        
//        XmlBeansSessionFactoryStandalone sessionFactory;
//        sessionFactory = new XmlBeansSessionFactoryStandalone();
//        sessionFactory.initialize();
//        
//        Session session = sessionFactory.currentSession();
//        
//        User user = (User) session.newEntity(User.class);
//        
//        user.setId("bastian");
//        user.setPassword("password");
//        
//        user.setName("Bastain");
//        user.setSurname("Steinert");
//        
//        Email email = user.addNewEmail();
//        email.setAddress("bastian.steinert@mindquarry.com");
//        email.setIsConversationRecipient(true);
//        
//        session.persist(user);
//               
//        List queryResult = session.query("getUserById", new Object[] {"bastian"});
//        User queriedUser = (User) queryResult.get(0);
//        assertEquals("bastian", queriedUser.getId());
    }
}
