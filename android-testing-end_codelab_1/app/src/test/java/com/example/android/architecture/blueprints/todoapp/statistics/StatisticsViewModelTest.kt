package com.example.android.architecture.blueprints.todoapp.statistics

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.android.architecture.blueprints.todoapp.MainCoroutineRule
import com.example.android.architecture.blueprints.todoapp.data.source.FakeTestRepository
import com.example.android.architecture.blueprints.todoapp.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class StatisticsViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var statisticsViewModel: StatisticsViewModel

    private lateinit var tasksRepository: FakeTestRepository

    @Before
    fun setup() {
        tasksRepository = FakeTestRepository()
        statisticsViewModel = StatisticsViewModel(tasksRepository)
    }

    @Test
    fun loadTasks_loading() {

        mainCoroutineRule.pauseDispatcher()
        statisticsViewModel.refresh()

        assertTrue(statisticsViewModel.dataLoading.getOrAwaitValue())
        mainCoroutineRule.resumeDispatcher()
        assertFalse(statisticsViewModel.dataLoading.getOrAwaitValue())

    }

    @Test
    fun loadStatisticsWhenTasksAreUnavailable_callErrorToDisplay() {
        tasksRepository.setReturnError(true)
        statisticsViewModel.refresh()

        assertTrue(statisticsViewModel.empty.getOrAwaitValue())
        assertTrue(statisticsViewModel.error.getOrAwaitValue())

    }


}