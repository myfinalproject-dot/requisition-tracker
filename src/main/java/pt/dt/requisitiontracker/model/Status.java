package pt.dt.requisitiontracker.model;

public enum Status {
    ACCEPTED {
        @Override
        public String toString() {
            return "ACCEPTED";
        }
    },
    REJECTED {
        @Override
        public String toString() {
            return "REJECTED";
        }
    }
}