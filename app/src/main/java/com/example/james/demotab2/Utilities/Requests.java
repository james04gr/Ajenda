package com.example.james.demotab2.Utilities;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


public class Requests {

    private String myUrl = "http://10.0.0.37:8080/dipl/";
    //private String myUrl = "http://192.168.1.2:8080/dipl/";


    //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    //----------------REQUESTS-----------------
    //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

    public void getSearchProfList(final String data, final VolleyCallbackSearchProfList callbackSearchProfList) throws JSONException {
        String url = myUrl + "FindAll";
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", data);
        System.out.println("Start Request....");
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println("Professor Return....");
                        callbackSearchProfList.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Error Request........ "+error.toString());
                        ErrorToast errorToast = new ErrorToast();
                        errorToast.ShowToastMsg("getSearchProfList with data " + data);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Log.v("checkLogin", "getParams");
                Map<String, String> params = new HashMap<>();
                params.put("data", data);
                return params;
            }

            @Override
            public byte[] getBody() {
                try {
                    byte[] bytes = jsonObject.toString().getBytes("UTF-8");
                    return bytes;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Log.v("checkLogin", "getHeaders");
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json;charset=utf-8");
                headers.put("Accept", "application/json;charset=utf-8");

                return headers;
            }

            @Override
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
                response.headers.put("Content-Type", "application/json;charset=utf-8");
                return super.parseNetworkResponse(response);
            }
        };
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonArrayRequest.setRetryPolicy(policy);
        RequestQueue queue = Volley.newRequestQueue(AjendaContext.getContext());
        queue.add(jsonArrayRequest);
    }

    public void getSearchSubjList(final String data, final VolleyCallbackSearchSubjList callbackSearchSubjList) throws JSONException {
        String url = myUrl + "FindAll";
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", data);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        callbackSearchSubjList.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ErrorToast errorToast = new ErrorToast();
                        errorToast.ShowToastMsg("getSearchSubjList with data " + data);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Log.v("checkLogin", "getParams");
                Map<String, String> params = new HashMap<>();

                params.put("data", data);

                return params;
            }

            @Override
            public byte[] getBody() {
                try {
                    byte[] bytes = jsonObject.toString().getBytes("UTF-8");
                    return bytes;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Log.v("checkLogin", "getHeaders");
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json;charset=utf-8");
                headers.put("Accept", "application/json;charset=utf-8");

                return headers;
            }

            @Override
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
                response.headers.put("Content-Type", "application/json;charset=utf-8");
                return super.parseNetworkResponse(response);
            }
        };
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonArrayRequest.setRetryPolicy(policy);
        RequestQueue queue = Volley.newRequestQueue(AjendaContext.getContext());
        queue.add(jsonArrayRequest);
    }

    public void getSearchSecList(final String data, final VolleyCallbackSearchSecList callbackSearchSecList) throws JSONException {
        String url = myUrl + "FindAll";
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", data);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        callbackSearchSecList.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ErrorToast errorToast = new ErrorToast();
                        errorToast.ShowToastMsg("getSearchSecList with data " + data);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Log.v("checkLogin", "getParams");
                Map<String, String> params = new HashMap<>();

                params.put("data", data);

                return params;
            }

            @Override
            public byte[] getBody() {
                try {
                    byte[] bytes = jsonObject.toString().getBytes("UTF-8");
                    return bytes;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Log.v("checkLogin", "getHeaders");
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json;charset=utf-8");
                headers.put("Accept", "application/json;charset=utf-8");

                return headers;
            }

            @Override
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
                response.headers.put("Content-Type", "application/json;charset=utf-8");
                return super.parseNetworkResponse(response);
            }
        };
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonArrayRequest.setRetryPolicy(policy);
        RequestQueue queue = Volley.newRequestQueue(AjendaContext.getContext());
        queue.add(jsonArrayRequest);
    }

    public void getProfessorSectionOffice(final int professorID, final VolleyCallbackSectionOffice volleyCallbackSectionOffice) throws JSONException {
        String url = myUrl + "FindSectionOffice";
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("professorID", professorID);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                volleyCallbackSectionOffice.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ErrorToast errorToast = new ErrorToast();
                errorToast.ShowToastMsg("getProfessorSectionOffice with id == " + professorID);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Log.v("checkLogin", "getParams");
                Map<String, String> params = new HashMap<>();

                params.put("data", String.valueOf(professorID));

                return params;
            }

            @Override
            public byte[] getBody() {
                try {
                    byte[] bytes = jsonObject.toString().getBytes("UTF-8");
                    return bytes;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Log.v("checkLogin", "getHeaders");
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json;charset=utf-8");
                headers.put("Accept", "application/json;charset=utf-8");

                return headers;
            }

            @Override
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {

                response.headers.put("Content-Type", "application/json;charset=utf-8");

                return super.parseNetworkResponse(response);
            }
        };
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonArrayRequest.setRetryPolicy(policy);
        RequestQueue queue = Volley.newRequestQueue(AjendaContext.getContext());
        queue.add(jsonArrayRequest);

    }

    public void getProfessorSubjectList(final int professorID, final VolleyCallbackProfessorSubjectList volleyCallbackProfessorSubjectList) throws JSONException {
        String url = myUrl + "FindCoursesProfessor";
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("professorID", professorID);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                volleyCallbackProfessorSubjectList.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ErrorToast errorToast = new ErrorToast();
                errorToast.ShowToastMsg("getProfessorSubjectList with id == " + professorID);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Log.v("checkLogin", "getParams");
                Map<String, String> params = new HashMap<>();
                params.put("professorID", String.valueOf(professorID));
                return params;
            }

            @Override
            public byte[] getBody() {
                try {
                    byte[] bytes = jsonObject.toString().getBytes("UTF-8");
                    return bytes;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Log.v("checkLogin", "getHeaders");
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json;charset=utf-8");
                headers.put("Accept", "application/json;charset=utf-8");

                return headers;
            }

            @Override
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {

                response.headers.put("Content-Type", "application/json;charset=utf-8");

                return super.parseNetworkResponse(response);
            }
        };
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonArrayRequest.setRetryPolicy(policy);
        RequestQueue queue = Volley.newRequestQueue(AjendaContext.getContext());
        queue.add(jsonArrayRequest);

    }

    public void getSubjectProfessorList(final int courseID, final VolleyCallbackSubjectProfessorList volleyCallbackSubjectProfessorList) throws JSONException {
        String url = myUrl + "FindProfessorsCourse";
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("courseID", courseID);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                volleyCallbackSubjectProfessorList.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ErrorToast errorToast = new ErrorToast();
                errorToast.ShowToastMsg("getProfessorSubjectList with id == " + courseID);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Log.v("checkLogin", "getParams");
                Map<String, String> params = new HashMap<>();
                params.put("professorID", String.valueOf(courseID));
                return params;
            }

            @Override
            public byte[] getBody() {
                try {
                    byte[] bytes = jsonObject.toString().getBytes("UTF-8");
                    return bytes;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Log.v("checkLogin", "getHeaders");
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json;charset=utf-8");
                headers.put("Accept", "application/json;charset=utf-8");

                return headers;
            }

            @Override
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {

                response.headers.put("Content-Type", "application/json;charset=utf-8");

                return super.parseNetworkResponse(response);
            }
        };
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonArrayRequest.setRetryPolicy(policy);
        RequestQueue queue = Volley.newRequestQueue(AjendaContext.getContext());
        queue.add(jsonArrayRequest);
    }

    public void getSubjectInformation(final int courseID, final int sectionID, final int classroomID, final VolleyCallbackSubjectInfos volleyCallbackSubjectInfos) throws JSONException {
        String url = myUrl + "FindCourseTimeClass";
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("courseID", courseID);
        jsonObject.put("sectionID", sectionID);
        jsonObject.put("classRoomID", classroomID);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                volleyCallbackSubjectInfos.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ErrorToast errorToast = new ErrorToast();
                errorToast.ShowToastMsg("getSubjectInformation with id == " + courseID);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Log.v("checkLogin", "getParams");
                Map<String, String> params = new HashMap<>();

                params.put("courseID", String.valueOf(courseID));
                params.put("sectionID", String.valueOf(sectionID));
                params.put("classRoomID", String.valueOf(classroomID));

                return params;
            }

            @Override
            public byte[] getBody() {
                try {
                    byte[] bytes = jsonObject.toString().getBytes("UTF-8");
                    return bytes;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Log.v("checkLogin", "getHeaders");
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json;charset=utf-8");
                headers.put("Accept", "application/json;charset=utf-8");

                return headers;
            }

            @Override
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {

                response.headers.put("Content-Type", "application/json;charset=utf-8");

                return super.parseNetworkResponse(response);
            }
        };
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonArrayRequest.setRetryPolicy(policy);
        RequestQueue queue = Volley.newRequestQueue(AjendaContext.getContext());
        queue.add(jsonArrayRequest);
    }

    public void getSectionInformation(final int sectionID, final VolleyCallbackSectionInfos volleyCallbackSectionInfos) throws JSONException {
        String url = myUrl + "FindBoth";
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("sectionID", sectionID);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                volleyCallbackSectionInfos.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ErrorToast errorToast = new ErrorToast();
                errorToast.ShowToastMsg("getSectionInformation with id == " + sectionID);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Log.v("checkLogin", "getParams");
                Map<String, String> params = new HashMap<>();
                params.put("sectionID", String.valueOf(sectionID));
                return params;
            }

            @Override
            public byte[] getBody() {
                try {
                    byte[] bytes = jsonObject.toString().getBytes("UTF-8");
                    return bytes;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Log.v("checkLogin", "getHeaders");
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json;charset=utf-8");
                headers.put("Accept", "application/json;charset=utf-8");

                return headers;
            }

            @Override
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {

                response.headers.put("Content-Type", "application/json;charset=utf-8");

                return super.parseNetworkResponse(response);
            }
        };
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonArrayRequest.setRetryPolicy(policy);
        RequestQueue queue = Volley.newRequestQueue(AjendaContext.getContext());
        queue.add(jsonArrayRequest);
    }

    public void getCurrentSubject(final int classroomID, final VolleyCallbackCurrentSubject volleyCallbackCurrentSubject) throws JSONException {
        String url = myUrl + "FindCurrentCourses";
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("classroomID", classroomID);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    volleyCallbackCurrentSubject.onSuccess(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Log.v("checkLogin", "getParams");
                Map<String, String> params = new HashMap<>();
                params.put("classroomID", String.valueOf(classroomID));
                return params;
            }

            @Override
            public byte[] getBody() {
                try {
                    byte[] bytes = jsonObject.toString().getBytes("UTF-8");
                    return bytes;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Log.v("checkLogin", "getHeaders");
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json;charset=utf-8");
                headers.put("Accept", "application/json;charset=utf-8");

                return headers;
            }

            @Override
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {

                response.headers.put("Content-Type", "application/json;charset=utf-8");

                return super.parseNetworkResponse(response);
            }
        };
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonArrayRequest.setRetryPolicy(policy);
        RequestQueue queue = Volley.newRequestQueue(AjendaContext.getContext());
        queue.add(jsonArrayRequest);
    }

    public void getAllHotspots(final VolleyCallbackHotspots volleyCallbackHotspots) throws JSONException {
        String url = myUrl + "FindHotspots";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                volleyCallbackHotspots.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("onErrorListener ...!! ");
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Log.v("checkLogin", "getParams");
                Map<String, String> params = new HashMap<>();
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Log.v("checkLogin", "getHeaders");
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json;charset=utf-8");
                headers.put("Accept", "application/json;charset=utf-8");

                return headers;
            }

            @Override
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {

                response.headers.put("Content-Type", "application/json;charset=utf-8");

                return super.parseNetworkResponse(response);
            }
        };
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonArrayRequest.setRetryPolicy(policy);
        RequestQueue queue = Volley.newRequestQueue(AjendaContext.getContext());
        queue.add(jsonArrayRequest);

    }

    public void getHotspotID(final String gimbalID, final VolleyCallbackHotspotID volleyCallbackHotspotID) throws JSONException {
        String url = myUrl + "FindHotspotID";
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("gimbalID", gimbalID);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                volleyCallbackHotspotID.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Log.v("checkLogin", "getParams");
                Map<String, String> params = new HashMap<>();
                params.put("gimbalID", String.valueOf(gimbalID));
                return params;
            }

            @Override
            public byte[] getBody() {
                try {
                    byte[] bytes = jsonObject.toString().getBytes("UTF-8");
                    return bytes;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Log.v("checkLogin", "getHeaders");
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json;charset=utf-8");
                headers.put("Accept", "application/json;charset=utf-8");

                return headers;
            }

            @Override
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {

                response.headers.put("Content-Type", "application/json;charset=utf-8");

                return super.parseNetworkResponse(response);
            }
        };
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonArrayRequest.setRetryPolicy(policy);
        RequestQueue queue = Volley.newRequestQueue(AjendaContext.getContext());
        queue.add(jsonArrayRequest);
    }

    public void getVisitProfessor(final int professorID, final VolleyCallbackVisitProfessor volleyCallbackVisitProfessor) throws JSONException {
        String url = myUrl + "FindVisitProfessor";
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("professorID", professorID);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    volleyCallbackVisitProfessor.onSuccess(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Log.v("checkLogin", "getParams");
                Map<String, String> params = new HashMap<>();
                params.put("professorID", String.valueOf(professorID));
                return params;
            }

            @Override
            public byte[] getBody() {
                try {
                    byte[] bytes = jsonObject.toString().getBytes("UTF-8");
                    return bytes;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Log.v("checkLogin", "getHeaders");
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json;charset=utf-8");
                headers.put("Accept", "application/json;charset=utf-8");

                return headers;
            }

            @Override
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {

                response.headers.put("Content-Type", "application/json;charset=utf-8");

                return super.parseNetworkResponse(response);
            }
        };
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonArrayRequest.setRetryPolicy(policy);
        RequestQueue queue = Volley.newRequestQueue(AjendaContext.getContext());
        queue.add(jsonArrayRequest);
    }


    //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    //---------------INTERFACES------------------
    //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

    public interface VolleyCallbackSearchProfList {
        void onSuccess(JSONArray jsonArray);
    }

    public interface VolleyCallbackSearchSubjList {
        void onSuccess(JSONArray jsonArray);
    }

    public interface VolleyCallbackSearchSecList {
        void onSuccess(JSONArray jsonArray);
    }

    public interface VolleyCallbackSectionOffice {
        void onSuccess(JSONArray jsonArray);
    }

    public interface VolleyCallbackProfessorSubjectList {
        void onSuccess(JSONArray jsonArray);
    }

    public interface VolleyCallbackSubjectProfessorList {
        void onSuccess(JSONArray jsonArray);
    }

    public interface VolleyCallbackSubjectInfos {
        void onSuccess(JSONArray jsonArray);
    }

    public interface VolleyCallbackSectionInfos {
        void onSuccess(JSONArray jsonArray);
    }

    public interface VolleyCallbackCurrentSubject {
        void onSuccess(JSONArray jsonArray) throws JSONException;
    }

    public interface VolleyCallbackHotspots {
        void onSuccess(JSONArray jsonArray);
    }

    public interface VolleyCallbackHotspotID {
        void onSuccess(JSONArray jsonArray);
    }

    public interface VolleyCallbackVisitProfessor {
        void onSuccess(JSONArray jsonArray) throws JSONException;
    }
}
