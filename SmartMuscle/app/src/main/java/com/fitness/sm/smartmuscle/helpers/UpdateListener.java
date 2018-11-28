package com.fitness.sm.smartmuscle.helpers;

public class UpdateListener {
        private String type;
        private UpdateHandler handler;

        public UpdateListener(UpdateHandler handler){
            this.handler = handler;
        }

        public UpdateHandler getHandler() {
            return handler;
        }
}
