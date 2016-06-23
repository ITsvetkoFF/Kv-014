package edu.softserve.zoo.web.test.controller.endpoints;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * Represent a route in the system.
 * Contains the URI of a route and a method where the URI was declared.
 *
 * @author Bohdan Cherniakh
 */
public class Route {
    String uri;
    Method method;

    public Route(String uri, Method method) {
        this.uri = uri;
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route other = (Route) o;
        return Objects.equals(uri, other.uri) &&
                Objects.equals(method, other.method);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uri, method);
    }

    @Override
    public String toString() {
        return "Route {" +
                "URI='" + uri + '\'' +
                ", method=" + method.toString() +
                '}';
    }
}
