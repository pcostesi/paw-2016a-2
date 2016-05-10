package ar.edu.itba.services;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import ar.edu.itba.interfaces.IterationDao;
import ar.edu.itba.interfaces.StoryDao;
import ar.edu.itba.interfaces.StoryService;
import ar.edu.itba.interfaces.TaskDao;
import ar.edu.itba.models.Iteration;
import ar.edu.itba.models.Story;

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

	private String storyTitle = "This is a nice title";

	private String longString = "gdshjgshkgdshksgdhksgdhgsdkkhdsgkhgdkhgsdkhkhgdshkgdshksdghkdghskhkgsdkgsdh"
			+ "sjdfhkjsfhsdkjfhsjkdfhsjkdfhsdjkfhsdkjfhsdkjfhskdjfhsdf"
			+ "fgjdkhgdfjkhgdkjfshgdkfjhgjdfkhgdjfkhgjdkfhgkjdfhgkfdgfg";

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		ss = new StoryServiceImpl(storyDao, iterationDao, taskDao);
	}

	@Test
	public void testCreateStory() {
		Mockito.when(storyDao.storyExists(testIteration.iterationId(), storyTitle)).thenReturn(false);
		Mockito.when(iterationDao.iterationExists(testIteration.iterationId())).thenReturn(true);
		ss.create(testIteration, storyTitle);
		verify(storyDao, atLeastOnce()).createStory(testIteration.iterationId(), storyTitle);
	}

	@Test(expected = IllegalStateException.class)
	public void testCreateAlreadyExistingStory() {
		Mockito.when(storyDao.storyExists(testIteration.iterationId(), storyTitle)).thenReturn(true);
		Mockito.when(iterationDao.iterationExists(testIteration.iterationId())).thenReturn(true);
		ss.create(testIteration, storyTitle);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateStoryWithNullIteraiton() {
		Mockito.when(storyDao.storyExists(testIteration.iterationId(), storyTitle)).thenReturn(false);
		Mockito.when(iterationDao.iterationExists(testIteration.iterationId())).thenReturn(true);
		ss.create(null, storyTitle);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateStoryWithNullString() {
		Mockito.when(storyDao.storyExists(testIteration.iterationId(), storyTitle)).thenReturn(false);
		Mockito.when(iterationDao.iterationExists(testIteration.iterationId())).thenReturn(true);
		ss.create(testIteration, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateStoryWithEmptyString() {
		Mockito.when(storyDao.storyExists(testIteration.iterationId(), storyTitle)).thenReturn(false);
		Mockito.when(iterationDao.iterationExists(testIteration.iterationId())).thenReturn(true);
		ss.create(testIteration, "");
	}

	@Test(expected = IllegalStateException.class)
	public void testCreateStoryWithinInexistingIteration() {
		Mockito.when(storyDao.storyExists(testIteration.iterationId(), storyTitle)).thenReturn(false);
		Mockito.when(iterationDao.iterationExists(testIteration.iterationId())).thenReturn(false);
		ss.create(testIteration, storyTitle);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateStoryWithLongString() {
		Mockito.when(storyDao.storyExists(testIteration.iterationId(), storyTitle)).thenReturn(false);
		Mockito.when(iterationDao.iterationExists(testIteration.iterationId())).thenReturn(true);
		ss.create(testIteration, longString);
	}

	@Test
	public void getByIdCorrectlly() {
		Mockito.when(storyDao.storyExists(testStory.storyId())).thenReturn(true);
		Mockito.when(storyDao.getStoryById(testStory.storyId())).thenReturn(testStory);
		ss.getById(testStory.storyId());
		verify(storyDao, times(1)).getStoryById(testStory.storyId());
	}

	@Test(expected = IllegalArgumentException.class)
	public void getByIdwithInvalidId() {
		Mockito.when(storyDao.storyExists(testStory.storyId())).thenReturn(false);
		Mockito.when(storyDao.getStoryById(testStory.storyId())).thenReturn(testStory);
		ss.getById(-10);
	}
	
	@Test(expected = IllegalStateException.class)
	public void getByIdwithInexistigStory() {
		Mockito.when(storyDao.storyExists(testStory.storyId())).thenReturn(false);
		Mockito.when(storyDao.getStoryById(testStory.storyId())).thenReturn(null);
		ss.getById(testStory.storyId());
	}
	
	@Test
	public void setNameTest(){
		int parentId = 10;
		Mockito.when(storyDao.storyExists(testStory.storyId())).thenReturn(true);
		Mockito.when(storyDao.getParentId(testStory.storyId())).thenReturn(parentId);
		Mockito.when(storyDao.storyExists(testStory.storyId(), storyTitle)).thenReturn(true);
		Mockito.when(testStory.title()).thenReturn(storyTitle);
		ss.setName(testStory, storyTitle + "addingStuff");
		verify(storyDao, times(1)).updateName(testStory.storyId(), storyTitle + "addingStuff");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setNameTestWithNullString(){
		int parentId = 10;
		Mockito.when(storyDao.storyExists(testStory.storyId())).thenReturn(true);
		Mockito.when(storyDao.getParentId(testStory.storyId())).thenReturn(parentId);
		Mockito.when(storyDao.storyExists(parentId, storyTitle)).thenReturn(true);
		ss.setName(testStory, null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setNameTestWithEmptyString(){
		int parentId = 10;
		Mockito.when(storyDao.storyExists(testStory.storyId())).thenReturn(false);
		Mockito.when(storyDao.getParentId(testStory.storyId())).thenReturn(parentId);
		Mockito.when(storyDao.storyExists(parentId, storyTitle)).thenReturn(false);
		ss.setName(testStory, "");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setNameTestWithLongString(){
		int parentId = 10;
		Mockito.when(storyDao.storyExists(testStory.storyId())).thenReturn(false);
		Mockito.when(storyDao.getParentId(testStory.storyId())).thenReturn(parentId);
		Mockito.when(storyDao.storyExists(parentId, storyTitle)).thenReturn(false);
		ss.setName(testStory, longString);
	}
	
	@Test(expected = IllegalStateException.class)
	public void setNameTestWithInexistentStory(){
		int parentId = 10;
		Mockito.when(storyDao.storyExists(testStory.storyId())).thenReturn(false);
		Mockito.when(storyDao.getParentId(testStory.storyId())).thenReturn(parentId);
		Mockito.when(storyDao.storyExists(parentId, storyTitle)).thenReturn(false);
		ss.setName(testStory, storyTitle);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setNameTestWithNullStory(){
		int parentId = 10;
		Mockito.when(storyDao.storyExists(testStory.storyId())).thenReturn(false);
		Mockito.when(storyDao.getParentId(testStory.storyId())).thenReturn(parentId);
		Mockito.when(storyDao.storyExists(parentId, storyTitle)).thenReturn(false);
		ss.setName(null, storyTitle);
	}
	
	@Test
	public void deleteSToryTest(){
		Mockito.when(storyDao.storyExists(testStory.storyId())).thenReturn(true);
		ss.deleteStory(testStory);
		verify(storyDao, times(1)).deleteStory(testStory.storyId());
	}

	@Test(expected = IllegalArgumentException.class)
	public void deleteSToryWithNullStory(){
		Mockito.when(storyDao.storyExists(testStory.storyId())).thenReturn(true);
		ss.deleteStory(null);
	}
	
	@Test(expected = IllegalStateException.class)
	public void deleteSToryWithInexistentStory(){
		Mockito.when(storyDao.storyExists(testStory.storyId())).thenReturn(false);
		ss.deleteStory(testStory);
	}
	
	@Test
	public void storyExistsTest(){
		Mockito.when(iterationDao.iterationExists(testIteration.iterationId())).thenReturn(true);
		Mockito.when(storyDao.storyExists(testStory.storyId(), storyTitle)).thenReturn(true);
		assertNotNull("Story should not be null", ss.storyExists(testIteration, storyTitle));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void storyExistsWithNullIteration(){
		Mockito.when(iterationDao.iterationExists(testIteration.iterationId())).thenReturn(true);
		Mockito.when(storyDao.storyExists(testStory.storyId(), storyTitle)).thenReturn(true);
		ss.storyExists(null, storyTitle);
	}
	
	@Test(expected = IllegalStateException.class)
	public void storyExistsWithInexistentIteration(){
		Mockito.when(iterationDao.iterationExists(testIteration.iterationId())).thenReturn(false);
		Mockito.when(storyDao.storyExists(testStory.storyId(), storyTitle)).thenReturn(true);
		ss.storyExists(testIteration, storyTitle);
	}
}
