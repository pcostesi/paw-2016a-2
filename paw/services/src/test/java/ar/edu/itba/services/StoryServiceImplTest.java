package ar.edu.itba.services;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ar.edu.itba.interfaces.dao.IterationDao;
import ar.edu.itba.interfaces.dao.StoryDao;
import ar.edu.itba.interfaces.dao.TaskDao;
import ar.edu.itba.interfaces.service.StoryService;
import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.Status;
import ar.edu.itba.models.Story;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class StoryServiceImplTest {

	private StoryService ss;
	
	@Mock
	private StoryDao storyDao;
	
	@Mock
	private TaskDao taskDao;
	
	@Mock
	private Story testStory;
	
	@Mock
	private IterationDao iterationDao;
	
	@Mock
	private Iteration testIteration;

	private final String storyTitle = "This is a nice title";

	private final String longString = "gdshjgshkgdshksgdhksgdhgsdkkhdsgkhgdkhgsdkhkhgdshkgdshksdghkdghskhkgsdkgsdh"
			+ "sjdfhkjsfhsdkjfhsjkdfhsjkdfhsdjkfhsdkjfhsdkjfhskdjfhsdf"
			+ "fgjdkhgdfjkhgdkjfshgdkfjhgjdfkhgdjfkhgjdkfhgkjdfhgkfdgfg";

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		ss = new StoryServiceImpl(storyDao, iterationDao, taskDao);
	}

	@Test
	public void testCreateStory() {
		Mockito.when(storyDao.storyExists(testIteration, storyTitle)).thenReturn(false);
		Mockito.when(iterationDao.iterationExists(testIteration)).thenReturn(true);
		ss.create(testIteration, storyTitle);
		verify(storyDao, atLeastOnce()).createStory(testIteration, storyTitle);
	}

	@Test(expected = IllegalStateException.class)
	public void testCreateAlreadyExistingStory() {
		Mockito.when(storyDao.storyExists(testIteration, storyTitle)).thenReturn(true);
		Mockito.when(iterationDao.iterationExists(testIteration)).thenReturn(true);
		ss.create(testIteration, storyTitle);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateStoryWithNullIteraiton() {
		Mockito.when(storyDao.storyExists(testIteration, storyTitle)).thenReturn(false);
		Mockito.when(iterationDao.iterationExists(testIteration)).thenReturn(true);
		ss.create(null, storyTitle);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateStoryWithNullString() {
		Mockito.when(storyDao.storyExists(testIteration, storyTitle)).thenReturn(false);
		Mockito.when(iterationDao.iterationExists(testIteration)).thenReturn(true);
		ss.create(testIteration, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateStoryWithEmptyString() {
		Mockito.when(storyDao.storyExists(testIteration, storyTitle)).thenReturn(false);
		Mockito.when(iterationDao.iterationExists(testIteration)).thenReturn(true);
		ss.create(testIteration, "");
	}

	@Test(expected = IllegalStateException.class)
	public void testCreateStoryWithinInexistingIteration() {
		Mockito.when(storyDao.storyExists(testIteration, storyTitle)).thenReturn(false);
		Mockito.when(iterationDao.iterationExists(testIteration)).thenReturn(false);
		ss.create(testIteration, storyTitle);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateStoryWithLongString() {
		Mockito.when(storyDao.storyExists(testIteration, storyTitle)).thenReturn(false);
		Mockito.when(iterationDao.iterationExists(testIteration)).thenReturn(true);
		ss.create(testIteration, longString);
	}

	@Test
	public void getByIdCorrectlly() {
		Mockito.when(storyDao.storyExists(testStory)).thenReturn(true);
		Mockito.when(storyDao.getStoryById(testStory.storyId())).thenReturn(testStory);
		ss.getById(testStory.storyId());
		verify(storyDao, times(1)).getStoryById(testStory.storyId());
	}

	@Test(expected = IllegalArgumentException.class)
	public void getByIdwithInvalidId() {
		Mockito.when(storyDao.storyExists(testStory)).thenReturn(false);
		Mockito.when(storyDao.getStoryById(testStory.storyId())).thenReturn(testStory);
		ss.getById(-10);
	}
	
	@Test(expected = IllegalStateException.class)
	public void getByIdwithInexistigStory() {
		Mockito.when(storyDao.storyExists(testStory)).thenReturn(false);
		Mockito.when(storyDao.getStoryById(testStory.storyId())).thenReturn(null);
		ss.getById(testStory.storyId());
	}
	
	@Test
	public void setNameTest(){
		Mockito.when(storyDao.storyExists(testStory)).thenReturn(true);
		Mockito.when(storyDao.getParent(testStory)).thenReturn(testIteration);
		Mockito.when(storyDao.storyExists(testIteration, storyTitle)).thenReturn(true);
		Mockito.when(testStory.title()).thenReturn(storyTitle);
		ss.setName(testStory, storyTitle + "addingStuff");
		verify(storyDao, times(1)).updateTitle(testStory, storyTitle + "addingStuff");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setNameTestWithNullString(){
		Mockito.when(storyDao.storyExists(testStory)).thenReturn(true);
		Mockito.when(storyDao.getParent(testStory)).thenReturn(testIteration);
		Mockito.when(storyDao.storyExists(testIteration, storyTitle)).thenReturn(true);
		ss.setName(testStory, null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setNameTestWithEmptyString(){
		Mockito.when(storyDao.storyExists(testStory)).thenReturn(false);
		Mockito.when(storyDao.getParent(testStory)).thenReturn(testIteration);
		Mockito.when(storyDao.storyExists(testIteration, storyTitle)).thenReturn(false);
		ss.setName(testStory, "");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setNameTestWithLongString(){
		Mockito.when(storyDao.storyExists(testStory)).thenReturn(false);
		Mockito.when(storyDao.getParent(testStory)).thenReturn(testIteration);
		Mockito.when(storyDao.storyExists(testIteration, storyTitle)).thenReturn(false);
		ss.setName(testStory, longString);
	}
	
	@Test(expected = IllegalStateException.class)
	public void setNameTestWithInexistentStory(){
		Mockito.when(storyDao.storyExists(testStory)).thenReturn(false);
		Mockito.when(storyDao.getParent(testStory)).thenReturn(testIteration);
		Mockito.when(storyDao.storyExists(testIteration, storyTitle)).thenReturn(false);
		ss.setName(testStory, storyTitle);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setNameTestWithNullStory(){
		Mockito.when(storyDao.storyExists(testStory)).thenReturn(false);
		Mockito.when(storyDao.getParent(testStory)).thenReturn(testIteration);
		Mockito.when(storyDao.storyExists(testIteration, storyTitle)).thenReturn(false);
		ss.setName(null, storyTitle);
	}
	
	@Test
	public void deleteSToryTest(){
		Mockito.when(storyDao.storyExists(testStory)).thenReturn(true);
		Mockito.when(storyDao.getParent(testStory)).thenReturn(testIteration);
		ss.deleteStory(testStory);
		verify(storyDao, times(1)).deleteStory(testStory);
	}

	@Test(expected = IllegalArgumentException.class)
	public void deleteSToryWithNullStory(){
		Mockito.when(storyDao.storyExists(testStory)).thenReturn(true);
		ss.deleteStory(null);
	}
	
	@Test(expected = IllegalStateException.class)
	public void deleteSToryWithInexistentStory(){
		Mockito.when(storyDao.storyExists(testStory)).thenReturn(false);
		ss.deleteStory(testStory);
	}
	
	@Test
	public void storyExistsTest(){
		Mockito.when(iterationDao.iterationExists(testIteration)).thenReturn(true);
		Mockito.when(storyDao.storyExists(testIteration, storyTitle)).thenReturn(true);
		assertNotNull("Story should not be null", ss.storyExists(testIteration, storyTitle));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void storyExistsWithNullIteration(){
		Mockito.when(iterationDao.iterationExists(testIteration)).thenReturn(true);
		Mockito.when(storyDao.storyExists(testIteration, storyTitle)).thenReturn(true);
		ss.storyExists(null, storyTitle);
	}
	
	@Test(expected = IllegalStateException.class)
	public void storyExistsWithInexistentIteration(){
		Mockito.when(iterationDao.iterationExists(testIteration)).thenReturn(false);
		Mockito.when(storyDao.storyExists(testIteration, storyTitle)).thenReturn(true);
		ss.storyExists(testIteration, storyTitle);
	}

	@Test(expected = IllegalStateException.class)
	public void addStoryToClosedIteration() {
		Mockito.when(iterationDao.iterationExists(testIteration)).thenReturn(false);
		Mockito.when(storyDao.storyExists(testIteration, storyTitle)).thenReturn(false);
		Mockito.when(testIteration.status()).thenReturn(Status.COMPLETED);
		ss.create(testIteration, storyTitle);
	}

	@Test(expected = IllegalArgumentException.class)
	public void getStoriesWithTaskForNullIteration() {
		ss.getStoriesWithTasksForIteration(null);
	}

	@Test(expected = IllegalStateException.class)
	public void getStoriesWithTaskForInexistentIteration() {
		Mockito.when(iterationDao.iterationExists(testIteration)).thenReturn(false);
		ss.getStoriesWithTasksForIteration(testIteration);
	}

	@Test
	public void getStoriesWithTaskSuccesfully() {
		Mockito.when(iterationDao.iterationExists(testIteration)).thenReturn(true);
		List<Story> storyList = new LinkedList<Story>();
		storyList.add(testStory); 
		Mockito.when(storyDao.getStoriesForIteration(testIteration)).thenReturn(storyList);
		ss.getStoriesWithTasksForIteration(testIteration);
		verify(taskDao, times(1)).getTasksForStory(testStory);
	}

	@Test(expected = IllegalStateException.class)
	public void changeStoryTitleToAnUsedTitle() {
		Mockito.when(storyDao.storyExists(testStory)).thenReturn(true);
		Mockito.when(testStory.title()).thenReturn(storyTitle);
		Mockito.when(storyDao.storyExists(storyDao.getParent(testStory), "Used title")).thenReturn(true);
		ss.setName(testStory, "Used title");
	}

	@Test(expected = IllegalStateException.class)
	public void changeStoryTitleToAFinishedIteration() {
		Mockito.when(storyDao.storyExists(testStory)).thenReturn(true);
		Mockito.when(testStory.title()).thenReturn(storyTitle);
		Mockito.when(storyDao.storyExists(storyDao.getParent(testStory), "Unused title")).thenReturn(true);
		Mockito.when(storyDao.getParent(testStory)).thenReturn(testIteration);
		Mockito.when(testIteration.status()).thenReturn(Status.COMPLETED);
		ss.setName(testStory, "Unused title");
	}

	@Test(expected = IllegalStateException.class)
	public void deleteStoryFromCompletedIteration() {
		Mockito.when(storyDao.storyExists(testStory)).thenReturn(true);
		Mockito.when(storyDao.getParent(testStory)).thenReturn(testIteration);
		Mockito.when(testIteration.status()).thenReturn(Status.COMPLETED);
		ss.deleteStory(testStory);
	}

}
