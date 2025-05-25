package hw11;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static hw11.Texts.out;

public class CollectionFamilyDao implements FamilyDao {

    private List<Family> families = new ArrayList<>();
    private final Logger logger;
    private static final String DATA_FILE = "families.json";

    public CollectionFamilyDao(Logger logger) {
        this.logger = logger;
    }

    public void saveData() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(DATA_FILE), families);
            logger.info(String.format("Saved %s families to file: %s", families.size(), DATA_FILE));
            out.printf("\nSaving...\n\nSaved %d families to file: %s\n", families.size(), DATA_FILE);
        } catch (IOException e) {
            logger.error(String.format("Failed to save families to file: %s, error: %s",DATA_FILE, e.getMessage()));
            out.printf("Failed to save families to file: %s, error: %s",DATA_FILE, e.getMessage());
        }
    }

    public void loadData(List<Family> families) {
        this.families = new ArrayList<>(families);
        logger.info(String.format("Loaded %s families from file: %s", families.size(), DATA_FILE));
    }

    @Override
    public List<Family> getAllFamilies() {
        return new  ArrayList<>(families);
    }

    @Override
    public Family getFamilyByIndex(int index) {
        if (index < 0 || index >= families.size()) return null;
        else return families.get(index);
    }

    @Override
    public boolean deleteFamily(int index) {
        if (index < 0 || index >= families.size()) return false;
        families.remove(index);
        return true;
    }

    @Override
    public boolean deleteFamily(Family family) {
        return families.remove(family);
    }

    @Override
    public void saveFamily(Family family) {
        int index = families.indexOf(family);
         if (index == -1) families.add(family);
         else families.set(index, family);
    }
}
