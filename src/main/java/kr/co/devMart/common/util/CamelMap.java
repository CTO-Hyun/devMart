package kr.co.devMart.common.util;


import org.apache.commons.collections4.map.ListOrderedMap;

public class CamelMap extends ListOrderedMap {
    private static final long serialVersionUID = 1L;

    @Override
    public Object put(Object key, Object value){
        return super.put(convert2CamelCase((String)key), value);
    }

    public String getDefault(Object key, String defaultValue) {
        if(this.get(key) == null) return defaultValue;
        return this.get(key).toString();
    }

    /**
     * underscore ('_') 가 포함되어 있는 문자열을 Camel Case ( 낙타등
     * 표기법 - 단어의 변경시에 대문자로 시작하는 형태. 시작은 소문자) 로 변환해주는
     * utility 메서드 ('_' 가 나타나지 않고 첫문자가 대문자인 경우도 변환 처리
     * 함.)
     * @param underScore
     *        - '_' 가 포함된 변수명
     * @return Camel 표기법 변수명
     */
    private static String convert2CamelCase(String underScore) {
        // '_' 가 나타나지 않으면 이미 camel case 로 가정함.
        // 단 첫째문자가 대문자이면 camel case 변환 (전체를 소문자로) 처리가
        // 필요하다고 가정함. --> 아래 로직을 수행하면 바뀜
        if (underScore.indexOf('_') < 0
                && Character.isLowerCase(underScore.charAt(0))) {
            return underScore;
        }
        StringBuilder result = new StringBuilder();
        boolean nextUpper = false;
        int len = underScore.length();

        for (int i = 0; i < len; i++) {
            char currentChar = underScore.charAt(i);
            if (currentChar == '_') {
                nextUpper = true;
            } else {
                if (nextUpper) {
                    result.append(Character.toUpperCase(currentChar));
                    nextUpper = false;
                } else {
                    result.append(Character.toLowerCase(currentChar));
                }
            }
        }
        return result.toString();
    }

    public String getString(String key, String defalutValue){
        if(super.get(key) == null) {
            return defalutValue;
        }

        return super.get(key).toString();
    }
    public int getInt(String key, int defalutValue){
        if(super.get(key) == null) {
            return defalutValue;
        }

        return Integer.parseInt(super.get(key).toString());
    }

    public Double getDouble(String key, double defalutValue){
        if(super.get(key) == null) {
            return defalutValue;
        }

        return Double.parseDouble(super.get(key).toString());
    }
}
