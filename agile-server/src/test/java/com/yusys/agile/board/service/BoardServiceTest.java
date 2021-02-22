package com.yusys.agile.board.service;

import com.yusys.agile.issue.dto.IssueDTO;
import com.yusys.agile.sprint.dao.SprintMapper;
import com.yusys.agile.sprint.dao.TaskBoardMapper;
import com.yusys.agile.sprint.domain.SprintWithBLOBs;
import com.yusys.agile.sprint.dto.BoardStoryParam;
import com.yusys.agile.sprint.service.impl.BoardServiceImpl;
import com.yusys.portal.facade.client.api.IFacadeUserApi;
import org.junit.Before;;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


//@RunWith( SpringRunner.class )
//@SpringBootTest(classes = {YusysAgileApplication.class})
//@RunWith( PowerMockRunner.class) --备注
//@PrepareForTest( BoardServiceImpl.class )
public class BoardServiceTest {
    private static final Logger log = LoggerFactory.getLogger(BoardServiceTest.class);
    @InjectMocks
    private BoardServiceImpl boardServicePy;

    @Mock
    private TaskBoardMapper taskBoardMapper;
    @Mock
    private SprintMapper sprintMapper;
    @Mock
    private IFacadeUserApi iFacadeUserApi;

    @Before
    public void setup() {
        //这句话执行以后，bookDao等bookService依赖的bean会自动注入到abcService中。
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getStoryWithTaskTest() {
        try {
            BoardStoryParam storyParam = new BoardStoryParam();
            storyParam.setSprintId(89L);
            SprintWithBLOBs sprint = new SprintWithBLOBs();
            sprint.setWorkHours(0);
            Mockito.when(sprintMapper.selectByPrimaryKey(Mockito.anyLong())).thenReturn(sprint);
            List<IssueDTO> storyWithTasks = boardServicePy.getStoryWithTask(storyParam);
            log.info("Junit测试--查询看板搜索故事和任务成功：{}", storyWithTasks);
        } catch (Exception e) {
            log.info("Junit测试--查询看板搜索故事和任务异常：{}", e);
        }

    }
}