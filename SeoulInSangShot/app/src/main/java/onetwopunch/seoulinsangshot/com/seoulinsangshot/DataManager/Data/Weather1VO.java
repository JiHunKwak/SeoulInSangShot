package onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Data;

import java.util.ArrayList;

import onetwopunch.seoulinsangshot.com.seoulinsangshot.Model.Model_Weather1;


/**
 * Created by Beom2 on 2017-09-24.
 */

public class Weather1VO {
    Response response;

    public class Response {

        Header header;
        Body body;
        public class Header {
            String resultCode;
            String resultMsg;

            public String getResultCode() {
                return resultCode;
            }

            public String getResultMsg() {
                return resultMsg;
            }
        }
        public class Body {
            Items items;

            public class Items {
                ArrayList<Model_Weather1> item= new ArrayList<Model_Weather1>();

                public ArrayList<Model_Weather1> getItem() {
                    return item;
                }

            }
            public Items getItems() {
                return items;
            }
        }
        public Header getHeader() {
            return header;
        }

        public Body getBody() {
            return body;
        }

    }

    public Response getResponse() {
        return response;
    }
}

