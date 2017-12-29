package com.pos.commons;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

/**
 * Created by rrampall on 29/12/17.
 */

public class Response<T> {

    private static final Logger logger = LoggerFactory.getLogger(Response.class);
    private HttpStatus httpStatus;
    private HttpHeaders httpHeaders;

    public Response() {
        httpHeaders = new HttpHeaders();
    }

    public Response<T> status(int status) {
        this.httpStatus = HttpStatus.valueOf(status);
        return this;
    }

    public Response<T> header(String name, String value) {
        httpHeaders.set(name, value);
        return this;
    }

    public Response<T> location(URI location) {
        httpHeaders.setLocation(location);
        return this;
    }

    public Response<T> location(String path, Object... values) {
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path(path).build().expand(values).encode().toUri();
        httpHeaders.setLocation(uri);
        return this;
    }

    public Response<T> created(URI location) {
        this.httpStatus = HttpStatus.CREATED;
        httpHeaders.setLocation(location);
        return this;
    }

    public Response<T> created(String path, Object... values) {
        return setStatusWithLocation(HttpStatus.CREATED, path, values);
    }

    public Response<T> accepted(String path, Object... values) {
        return setStatusWithLocation(HttpStatus.ACCEPTED, path, values);
    }

    private Response<T> setStatusWithLocation(HttpStatus httpStatus, String path, Object... values) {
        this.httpStatus = httpStatus;

        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path(path).build().expand(values).encode().toUri();
        httpHeaders.setLocation(uri);
        return this;
    }

    public Response<T> noContent() {
        this.httpStatus = HttpStatus.NO_CONTENT;
        return this;
    }

    public Response<T> ok() {
        this.httpStatus = HttpStatus.OK;
        return this;
    }

    public ResponseEntity<HttpStatus> build() {
        return new ResponseEntity<HttpStatus>(httpHeaders, httpStatus);
    }

    public ResponseEntity<T> buildWithoutAutoSets(T resource) {
        URI uri;

        logger.debug("Start time for buildWithoutAutoSets() --> {}", Calendar.getInstance().getTime());

        if (httpHeaders.getLocation() != null) {
            uri = httpHeaders.getLocation();
        } else {
            if (getCurrentRequest().getQueryString() != null) {
                String path = ServletUriComponentsBuilder.fromCurrentRequestUri().build().encode().toUri().toString() + "?" + getCurrentRequest().getQueryString();
                try {
                    uri = new URI(path);
                } catch (URISyntaxException e) {
                    uri = ServletUriComponentsBuilder.fromCurrentRequestUri().build().encode().toUri();
                }
            } else {
                uri = ServletUriComponentsBuilder.fromCurrentRequestUri().build().encode().toUri();
            }
        }

      //  ResourceUtil.setResourceUri(resource, uri);

        saveEventObject(resource);

        logger.debug("End time for buildWithoutAutoSets() --> {}", Calendar.getInstance().getTime());

        return new ResponseEntity<T>(resource, httpHeaders, httpStatus);
    }

    public ResponseEntity<T> build(T resource) {
        URI uri = null;

        if (httpHeaders.getLocation() != null) {
            uri = httpHeaders.getLocation();
        } else {
            if (getCurrentRequest().getQueryString() != null) {
                String path = ServletUriComponentsBuilder.fromCurrentRequestUri().build().encode().toUri().toString() + "?" + getCurrentRequest().getQueryString();
                try {
                    uri = new URI(path);
                } catch (URISyntaxException e) {
                    uri = ServletUriComponentsBuilder.fromCurrentRequestUri().build().encode().toUri();
                }
            } else {
                uri = ServletUriComponentsBuilder.fromCurrentRequestUri().build().encode().toUri();
            }
        }

      //  ResourceUtil.setResourceUri(resource, uri);
        for (Field field : resource.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            // check if it's a collection of resources.
            if (Collection.class.isAssignableFrom(field.getType())) {
                try {
                    // iterate through and populate resource
                    // if it has id which is not null or empty and URI which is not null or empty
                    // we populate URI taking one for parent and appending /id
                    ArrayList arrayList = (ArrayList) field.get(resource);
                    if (arrayList != null) {
                        for (Object item : arrayList) {
                            // getting superclass here, as only it had id and resource field accessible
                            String idString = (String) item.getClass().getMethod("getId").invoke(item);
                            URI resourceString = (URI) item.getClass().getMethod("getResource").invoke(item);
                            if (!StringUtils.isEmpty(idString) && (resourceString == null || StringUtils.isEmpty(resourceString.toString()))) {
                     //           ResourceUtil.setResourceUri(item, ServletUriComponentsBuilder.fromUri(uri).path("/" + idString).build().encode().toUri());
                            }
                        }
                    }
                } catch (ClassCastException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                    // we skip any exceptions, because the object we receive may not be a page/base resource.
                }

            }
        }
        saveEventObject(resource);

        return new ResponseEntity<T>(resource, httpHeaders, httpStatus);
    }

    private void saveEventObject(T resource) {
        getCurrentRequest().setAttribute("event", resource);
    }

    // copied from ServletUriComponentsBuilder
    protected static HttpServletRequest getCurrentRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        Assert.state(requestAttributes != null, "Could not find current request via RequestContextHolder");
        Assert.isInstanceOf(ServletRequestAttributes.class, requestAttributes);
        HttpServletRequest servletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
        Assert.state(servletRequest != null, "Could not find current HttpServletRequest");
        return servletRequest;
    }


}
