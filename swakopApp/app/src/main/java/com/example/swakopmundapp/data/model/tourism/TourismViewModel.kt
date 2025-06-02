package com.example.swakopmundapp.data.model.tourism

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swakopmundapp.data.repository.TourismRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TourismViewModel(
    private val repository: TourismRepository
) : ViewModel() {

    private val _places = MutableStateFlow<List<TourismActivity>>(emptyList())
    val places: StateFlow<List<TourismActivity>> = _places.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _activities = MutableStateFlow<List<ApiActivity>>(emptyList())
    val activities: StateFlow<List<ApiActivity>> = _activities.asStateFlow()

    private val _isLoadingActivities = MutableStateFlow(false)
    val isLoadingActivities: StateFlow<Boolean> = _isLoadingActivities.asStateFlow()

    private val _errorActivities = MutableStateFlow<String?>(null)
    val errorActivities: StateFlow<String?> = _errorActivities.asStateFlow()

    private var searchActivitiesJob: Job? = null

    init {
        loadPlaces()
        searchAppActivities(null)
    }

    private fun loadPlaces() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val fetchedPlaces = repository.getPlaces()
                _places.value = fetchedPlaces
            } catch (e: Exception) {
                _error.value = e.message ?: "Unknown error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun refreshPlaces() {
        loadPlaces()
    }

    fun getPlaceByName(name: String): TourismActivity? {
        return _places.value.find { it.name == name }
    }

    fun onSearchTermChanged(searchTerm: String?) {
        searchAppActivities(searchTerm)
    }

    private fun searchAppActivities(searchTerm: String?) {
        searchActivitiesJob?.cancel()
        searchActivitiesJob = viewModelScope.launch {
            if (!searchTerm.isNullOrBlank()) {
                delay(350L)
            }
            _isLoadingActivities.value = true
            _errorActivities.value = null
            try {
                val termToSearch = if (searchTerm.isNullOrBlank()) null else searchTerm
                _activities.value = repository.searchActivities(termToSearch)
            } catch (e: Exception) {
                _errorActivities.value = "Failed to load activities: ${e.message ?: "Unknown error"}"
            } finally {
                _isLoadingActivities.value = false
            }
        }
    }
}
