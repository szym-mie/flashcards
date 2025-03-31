package pl.agh.to.lang.helpers;

import java.util.List;

public enum PartOfSpeech {
    NOUN, // rzeczownik
    VERB, // czasownik
    ADJECTIVE, // przymiotnik
    ADVERB, // przysłówek
    NUMERAL, // liczebnik
    PRONOUN, // zaimek
    PREPOSITION, // przyimek
    CONJUNCTION, // spójnik
    PARTICLE, // partykuła
    INTERJECTION, // wykrzyknik
    ARTICLE, // rodzajnik
    OTHER;

    public List<PartOfSentence> getMatchingPartsOfSentence() {
        return switch (this) {
            case NOUN, PRONOUN -> List.of(PartOfSentence.SUBJECT, PartOfSentence.OBJECT, PartOfSentence.ADVERBIAL_MODIFIER, PartOfSentence.ATTRIBUTE, PartOfSentence.OTHER);
            case VERB -> List.of(PartOfSentence.PREDICATE, PartOfSentence.OTHER);
            case ADJECTIVE -> List.of(PartOfSentence.SUBJECT, PartOfSentence.ATTRIBUTE, PartOfSentence.PREDICATE, PartOfSentence.OTHER);
            case ADVERB -> List.of(PartOfSentence.PREDICATE, PartOfSentence.ADVERBIAL_MODIFIER, PartOfSentence.OTHER);
            case NUMERAL -> List.of(PartOfSentence.SUBJECT, PartOfSentence.OBJECT, PartOfSentence.ATTRIBUTE, PartOfSentence.OTHER);
            case PREPOSITION -> List.of(PartOfSentence.ADVERBIAL_MODIFIER, PartOfSentence.OTHER);
            case CONJUNCTION -> List.of(PartOfSentence.COORDINATE, PartOfSentence.SUBORDINATE, PartOfSentence.OTHER);
            case PARTICLE, INTERJECTION, ARTICLE -> List.of(PartOfSentence.OTHER);
            case OTHER -> List.of(PartOfSentence.values());
        };
    }
}
