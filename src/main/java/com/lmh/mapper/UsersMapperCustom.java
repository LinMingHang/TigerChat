package com.lmh.mapper;

import com.lmh.pojo.Users;
import com.lmh.pojo.vo.FriendRequestVO;
import com.lmh.pojo.vo.MyFriendsVO;
import com.lmh.utils.MyMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: UsersMapperCustom
 * @Description: TODO
 * @author: ALin
 * @date: 2020/3/31 上午11:48
 */
@Repository
public interface UsersMapperCustom extends MyMapper<Users> {

    public List<FriendRequestVO> queryFriendRequestList(String acceptUserId);

    public List<MyFriendsVO> queryMyFriends(String userId);

    public void batchUpdateMsgSigned(List<String> msgIdList);

}