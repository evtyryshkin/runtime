package ru.tyryshkin.core.test

object TestTag {
    object BottomNavigationTab {
        const val PROFILE = "tab_profile"
        const val TRACKER = "tab_tracker"
        const val SETTINGS = "tab_settings"
    }

    object Tracker {
        const val BUTTON_START = "button_start"

        object Race {
            const val TEXT_COUNT_DOWN = "text_count_down"
        }
    }

    object Settings {
        const val TRACKER = "settings_tracker"

        object Tracker {
            const val COUNT_DOWN = "settings_count_down"
            const val MODAL_BOTTOM_SHEET_COUNT_DOWN_VALUE = "count_down_value_"
        }
    }

    object Component {
        object Cell {
            const val TITLE = "r_cell_title"
            const val SUBTITLE = "r_cell_subtitle"
        }

        object AppBar {
            const val BUTTON_BACK = "button_back"
        }
    }
}
