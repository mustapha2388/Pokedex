package com.example.pokedex.model;

import androidx.annotation.NonNull;

import com.squareup.moshi.Json;

import java.util.List;

@SuppressWarnings("unused")
public class Pokemon {
    @Json(name = "name")
    private String name;

    @Json(name = "id")
    private int id;

    @Json(name = "sprites")
    private Sprite sprite;

    @Json(name = "types")
    private List<Type> types;

    @Json(name = "height")
    private int height;
    @Json(name = "weight")
    private int weight;

    @Json(name = "abilities")
    private List<Ability> abilities;


    @Json(name = "species")
    private Specie specie;

    private String description;

    @Json(name = "stats")
    private List<Stat> stats;


    public List<Stat> getStats() {
        return stats;
    }

    public void setStats(List<Stat> stats) {
        this.stats = stats;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<Ability> abilities) {
        this.abilities = abilities;
    }

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }


    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Specie getSpecie() {
        return specie;
    }

    public void setSpecie(Specie specie) {
        this.specie = specie;
    }

    @NonNull
    @Override
    public String toString() {
        return "Pokemon{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", sprite=" + sprite +
                ", types=" + types +
                ", height=" + height +
                ", weight=" + weight +
                ", abilities=" + abilities +
                ", specie=" + specie +
                ", description='" + description + '\'' +
                ", stats=" + stats +
                '}';
    }
}
