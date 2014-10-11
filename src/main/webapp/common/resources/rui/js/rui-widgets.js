/* 
 * Copyright section: TODO
 */

/* 
 * RUI: General RUI widgets
 */
if(!window.Rui){
    window.Rui = {};
}
Rui.widgets = {};

Rui.widgets.GridPanel = function( tableId ){
	if( tableId == undefined ){
		var g = $( '.grid' );
		if(g.length == 1){
			this.gridPanelEl = g[0];
		}
	}
	else{
		this.gridPanelEl = $( "#"+tableId )[0];	
	}
};

Rui.widgets.GridPanel.prototype = {
	selectMultiple: false,
	gridPanelEl: null,
	currentRow: null,
	init: function(){
		var me = this;
		var rows = $( 'tr:has(td)', this.gridPanelEl ).addClass( 'selectable' );
		rows.on('click', 'td', {me: me}, me.onRowClick);//.unselectable();
		var rowNoCells = $( '.row-no', this.gridPanelEl ).on('click', {me: me}, me.onRowNoClick);
        rows.each( function(i, item){
    		item.oIndex = i;
    		item.unselectable = true;
    		$( item ).on( 'selectstart dragstart', me.stopSelectFn );
        });
	},
	setSelectMultiple: function( multiple ){
		this.selectMultiple = multiple;
	},
	unselectAll: function(){
		var me = this;
		var rows = $( '.selectable', me.gridPanelEl ).removeClass('row-selected');
		var rowNoCells = $( '.row-no', me.gridPanelEl ).removeClass('row-no-selected');
		rows.each(function(i, item){ item.unselectable=true; });
		me.currentRow = null;
	},
    stopSelectFn: function( e ){
        var t = $( e.target ).parent()[0];
        if( t.tagName == 'TR' && t.unselectable ){
            e.stopPropagation();
            e.preventDefault();
            return false;
        }
        else{
            return true;
        }
    },
    setHtmlSelectable: function( selectable ){
        var el = $( '.selectable', this.gridPanelEl );
        if( selectable ){
            el.un( 'selectstart dragstart', this.stopSelectFn );
        }
        else{
            el.on( 'selectstart dragstart', this.stopSelectFn );
        }
    },
    onKeyDown: function(e){
        var me = e.data.me;
        var t = e.target;
        var tagName = t.tagName.toLowerCase();
        if(e.shiftKey && tagName == 'table' ){
            me.setHtmlSelectable( false );
            e.preventDefault();
            e.stopPropagation();
        }
    },
    onKeyUp: function(e){
        var me = e.data.me;
        var t = e.target;
        var tagName = t.tagName.toLowerCase();
        if(e.shiftKey && tagName == 'table' ){
            me.setHtmlSelectable( true );
            e.preventDefault();
            e.stopPropagation();
        }
    },
	onRowClick: function(e){
        var me = e.data.me;
		var rowEl = $( e.target ).parent(); 
		var rowDom = rowEl[0];
		var noEl = rowEl.find('.row-no');

		if( !me.selectMultiple ){
			me.unselectAll();
			noEl.addClass('row-no-selected');
			rowEl.addClass('row-selected');
			me.currentRow = rowEl[0];
			return;
		}

		if( e.ctrlKey ){
			noEl.toggleClass('row-no-selected');			
			rowEl.toggleClass('row-selected');
			me.currentRow = rowEl[0];
		}
		else if( e.shiftKey ){
			var rows = $( 'tr:has(td)', this.gridPanelEl );
			rows.each( function(i, item){
				if(me.currentRow){
					if( ( me.currentRow.oIndex >= item.oIndex && rowDom.oIndex <= item.oIndex ) ||
						( me.currentRow.oIndex <= item.oIndex && rowDom.oIndex >= item.oIndex ) ) {
						var rowElt = $( item );
						var noElt = rowElt.find('.row-no');
						rowElt.addClass('row-selected');
						noElt.addClass('row-no-selected');
					}
				}
			});
			
			noEl.addClass('row-no-selected');
			rowEl.addClass('row-selected');
			me.currentRow = rowEl[0];
			e.preventDefault();
		}
		else {
			me.unselectAll();
			noEl.addClass('row-no-selected');
			rowEl.addClass('row-selected');
			me.currentRow = rowEl[0];
    		if( me.currentRow ){
    			//$( me.currentRow ).off( 'selectstart dragstart', me.stopSelectFn );
    			me.currentRow.unselectable  = false;
    		}
			return;
		}
		e.preventDefault();
        e.stopPropagation();
	},
	onRowNoClick: function(e){
        var me = e.data.me;
		var noEl = $( e.target );
		var rowEl = noEl.parent();
		me.currentRow = rowEl[0];
		
		if( !me.selectMultiple ){
			me.unselectAll();
			noEl.addClass('row-no-selected');
			rowEl.addClass('row-selected');
			return;
		}
		
		noEl.toggleClass('row-no-selected');			
		rowEl.toggleClass('row-selected');
		e.preventDefault();
		e.stopPropagation();
	},
	getSelectionIds: function(){
		var inputIds = $( '.row-no-selected input', this.gridPanelEl );
		var ids = new Array();
		inputIds.each(function(i, item){
			ids.push( $( item ).val() );
		});
		return ids;
	}
};
