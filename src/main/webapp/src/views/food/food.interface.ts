export interface FoodItem {
    name: string;
    mineralValues: Pair[];
    vitaminValues: Pair[];
}

export interface Pair {
    key: string;
    value: string;
}