package com.cw.pluralsight.advancedstreams.customCollectors;

import java.util.Objects;

public class Actor {
    public String lastName, firstName;

    public Actor(String lastName, String firstName) {
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public Actor() {
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Actor actor = (Actor) o;
        return Objects.equals(lastName, actor.lastName) && Objects.equals(firstName, actor.firstName);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67*hash +Objects.hashCode(this.lastName);
        hash = 67*hash + Objects.hashCode(this.firstName);
        return hash;
    }

    @Override
    public String toString() {
        return "Actor{" + "lastName='" + lastName + '\'' + ", firstName='" + firstName + '\'' + '}';
    }
}
