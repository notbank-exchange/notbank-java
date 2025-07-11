package exchange.notbank.trading.constants;

public enum OrdersState {
    Unknown {
        public String toString(){
            return "0";
        }
    },
    Working {
        public String toString(){
            return "1";
        }
    },
    Rejected {
        public String toString(){
            return "2";
        }
    },
    Canceled {
        public String toString(){
            return "3";
        }
    },
    Expired {
        public String toString(){
            return "4";
        }
    },
    FullyExecuted {
        public String toString(){
            return "5";
        }
    }
}
