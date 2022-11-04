package com.mob.backend.service.impl.user.bot;

import com.mob.backend.mapper.BotMapper;
import com.mob.backend.pojo.Bot;
import com.mob.backend.pojo.User;
import com.mob.backend.service.impl.utils.UserDetailsImpl;
import com.mob.backend.service.user.bot.UpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UpdateServiceImpl implements UpdateService {
    @Autowired
    private BotMapper botMapper;
    @Override
    public Map<String, String> update(Map<String, String> data) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl)  authenticationToken.getPrincipal();
        User user = loginUser.getUser();

        int bot_id = Integer.parseInt(data.get("bot_id"));

        String title = data.get("title");
        String description = data.get("description");
        String content = data.get("content");

        Map<String, String> map = new HashMap<>();

        if(title == null || title.length() == 0)
        {
            map.put("error_message", "title cannot be empty");
            return map;
        }

        if(title.length() > 100){
            map.put("error_message", "The length of title cannot extend 100");
            return map;
        }

        if(description == null || description.length() == 0){
            description = "Lazy user, nothing left here~";
        }
        if(description.length() > 300){
            map.put("error_message", "Bot's description cannot extend 300");
            return map;
        }

        if(content == null || content.length() == 0){
            map.put("error_message", "content cannot be empty");
            return map;
        }

        if(content.length() > 10000) {
            map.put("error_message", "Content's length cannot extend 10000");
            return map;
        }

        Bot bot = botMapper.selectById(bot_id);
        if(bot == null){
            map.put("error_message", " Bot is not existed or has been deleted before");
            return map;
        }

        if(!bot.getUserId().equals(user.getId())){
            map.put("error_message", "You are not permitted to do this");
            return map;
        }

        Bot new_bot = new Bot(
                bot.getId(),
                user.getId(),
                title,
                description,
                content,
                bot.getCreatetime(),
                new Date()
        );
        botMapper.updateById(new_bot);

        map.put("error_message", "update successfully");
        return  map;
    }
}
