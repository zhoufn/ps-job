$.WEB_ROOT = '';

var Common = (function(){

    /**
     * 根据字段排序表格数据
     * @param store        表格数据
     * @param sortBy       需要排序的字段
     * @param sortMode     升序、降序
     * @param renderer     渲染器
     * @private
     */
    var _sortTable = function(store, sortBy, sortMode,renderer){
        function compare(item1, item2){
            if(sortMode == 'sort-asc'){
                return item1[sortBy] > item2[sortBy];
            }else if(sortMode == 'sort-desc'){
                return item1[sortBy] < item2[sortBy];
            }
        }
        store.sort(compare);
        renderer(store);
    }

    return {
        sortTable: _sortTable
    }



})();