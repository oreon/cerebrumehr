
function handleDialogSubmit(xhr, status, args, dlg) {
	        if (args.validationFailed) {
	            //alert('invalid');
	        	dlg.show();
	        } else {
	        	//alert('valid');
	        	dlg.hide();
	        }
	    }



